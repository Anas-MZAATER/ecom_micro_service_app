package net.anas.billing_service;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.kv.model.GetValue;
import io.github.cdimascio.dotenv.Dotenv;
import net.anas.billing_service.clients.CustomerRestClient;
import net.anas.billing_service.clients.ProductRestClient;
import net.anas.billing_service.entities.Bill;
import net.anas.billing_service.entities.ProductItem;
import net.anas.billing_service.model.Customer;
import net.anas.billing_service.model.Product;
import net.anas.billing_service.repository.BillRepo;
import net.anas.billing_service.repository.ProductItemRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.Versioned;

import java.util.*;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

    public static void main(String[] args) {
        // Only load .env in local dev, ignored in Docker
        System.out.println("Working dir: " + System.getProperty("user.dir"));
        Dotenv dotenv = Dotenv.configure()
                .directory("..")
                .filename(".env")
                .ignoreIfMissing()  // ← key: won't crash in Docker where no .env exists
                .load();

        // Only set if not already set by Docker/OS
        setIfAbsent("SPRING_CLOUD_VAULT_TOKEN", dotenv, "");
        setIfAbsent("SPRING_CLOUD_VAULT_HOST",  dotenv, "localhost");
        setIfAbsent("SPRING_CLOUD_VAULT_PORT",  dotenv, "8200");
        setIfAbsent("SPRING_CLOUD_CONSUL_HOST", dotenv, "localhost");
        setIfAbsent("SPRING_CLOUD_CONSUL_PORT", dotenv, "8500");

        SpringApplication.run(BillingServiceApplication.class, args);
    }
    private static void setIfAbsent(String key, Dotenv dotenv, String defaultValue) {
        if (System.getProperty(key) == null && System.getenv(key) == null) {
            String value = dotenv.get(key, defaultValue);
            System.setProperty(key, value);
        }
    }

    @Bean
    CommandLineRunner start(BillRepo billRepo, ProductItemRepo productItemRepo,
                            CustomerRestClient customerRestClient,
                            ProductRestClient productRestClient) {
        return args -> {
            Collection<Product> products=productRestClient.allProducts().getContent();
            Long customerId=1L;
            Customer customer=customerRestClient.findCustomerById(customerId);
            if(customer==null) throw new RuntimeException("Customer not found");
            Bill bill=new Bill();
            bill.setCustomerId(customerId);
            bill.setBillDate(new Date());
            Bill savedBill=billRepo.save(bill);
            products.forEach(p->{
                ProductItem productItem=new ProductItem();
                productItem.setBill(savedBill);
                productItem.setProductId(p.getId());
                productItem.setQuantity(1+new Random().nextInt(10));
                productItem.setPrice(p.getPrice());
                productItem.setDiscount(Math.random());
                productItemRepo.save(productItem);
            });
        };
    }

    @Bean
    CommandLineRunner initVault(VaultTemplate vaultTemplate,
            @Value("${spring.cloud.vault.host}") String vaultHost) {
        return args -> {

            // WRITE
            Versioned.Metadata resq =vaultTemplate
                    .opsForVersionedKeyValue("secret")
                    .put("billing_service",
                            Map.of(
                                    "user.privateKey", "8888",
                                    "user.publicKey",  "9999",
                                    "user.username",   "anas",
                                    "user.password",   "123456",
                                    "user.otp",        "123"
                            ));

            // READ latest version
            System.out.println("=== Vault Configuration ===");
            Versioned<Map<String, Object>> response = vaultTemplate
                    .opsForVersionedKeyValue("secret")
                    .get("billing_service");

            if (response != null && response.hasData()) {
                Map<String, Object> data = response.getData();
                System.out.println("last Version : " +data);

                //String privateKey = (String) data.get("user.privateKey");
                //String publicKey  = (String) data.get("user.publicKey");
                //System.out.println("last Version : " + "privateKey=" + privateKey+", publicKey=" + publicKey);
            }

            // READ specific version
            Versioned<Map<String, Object>> v1 = vaultTemplate
                    .opsForVersionedKeyValue("secret")
                    .get("billing_service", Versioned.Version.from(1));

            System.out.println("Version 1 : " + v1.getData());
        };
    }
    @Bean
    CommandLineRunner initConsul(@Value("${spring.cloud.consul.host}") String consulHost,
                                 @Value("${spring.cloud.consul.port}") int consulPort) {
        return args -> {

            //String host = System.getenv().getOrDefault("CONSUL_HOST", "localhost");
            ConsulClient consulClient = new ConsulClient(consulHost, consulPort);

            String key = "config/billing-service";

            // WRITE
            Map<String, String> config = Map.of(
                    "token.accessTokenTimeout", "8888",
                    "token.refreshTokenTimeout", "9999"
            );

            config.forEach((k, v) -> {
                consulClient.setKVValue(key + "/" + k, v);
            });

            // READ all keys
            System.out.println("=== Consul configuration ===");
            Response<List<GetValue>> values = consulClient.getKVValues(key);

            if (values != null && values.getValue() != null) {
                for (GetValue gv : values.getValue()) {
                    String fullKey = gv.getKey();
                    String value = gv.getDecodedValue();

                    System.out.println(fullKey + " = " + value);
                }
            }

            // READ specific key
            Response<GetValue> refreshTokenTimeout = consulClient.getKVValue(key + "/token.refreshTokenTimeout");

            if (refreshTokenTimeout.getValue() != null) {
                System.out.println("refreshTokenTimeout = " + refreshTokenTimeout.getValue().getDecodedValue());
            }
        };
    }

}
