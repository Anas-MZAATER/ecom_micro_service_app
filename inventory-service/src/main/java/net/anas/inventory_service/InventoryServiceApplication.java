package net.anas.inventory_service;


import net.anas.inventory_service.entities.Product;
import net.anas.inventory_service.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.List;
import java.util.Random;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(ProductRepository productRepository, RepositoryRestConfiguration restConfiguration){
        return args -> {
            restConfiguration.exposeIdsFor(Product.class);
            Random random = new Random();
            for(int i=0;i<10;i++){
                productRepository.saveAll(
                        List.of(
                                Product.builder().name("Computer_"+i).price(1200+Math.random()*1000).quantity(1+random.nextInt(200)).build(),
                                Product.builder().name("Printer_"+i).price(1200+Math.random()*1000).quantity(1+random.nextInt(200)).build(),
                                Product.builder().name("Smartphone_"+i).price(1200+Math.random()*1000).quantity(1+random.nextInt(200)).build()
                        )
                );
            }
        };
    }

}
