package net.anas.order_service;

import net.anas.order_service.enums.OrderStatus;
import net.anas.order_service.modal.Customer;
import net.anas.order_service.modal.Product;
import net.anas.order_service.entities.Order;
import net.anas.order_service.entities.ProductItem;
import net.anas.order_service.repo.OrderRepo;
import net.anas.order_service.repo.ProductItemRepo;
import net.anas.order_service.services.CustomerRestClientService;
import net.anas.order_service.services.InventoryRestClientService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(OrderRepo orderRepo, ProductItemRepo productItemRepo,
							CustomerRestClientService customerRestClientService,
							InventoryRestClientService inventoryRestClientService) {
		return args -> {
			List<Customer> customers=customerRestClientService.allCustomers().getContent().stream().toList();
			List<Product> products=inventoryRestClientService.allProducts().getContent().stream().toList();
			Random random=new Random();
			for(int i=0;i<20;i++){
				Order order=Order.builder()
						.customerId(customers.get(random.nextInt(customers.size())).getId())
						.status(Math.random()>0.5? OrderStatus.CREATED:OrderStatus.PENDING)
						.createdAt(LocalDate.now())
						.build();
				Order savedOrder=orderRepo.save(order);
				for(int j=0;j<products.size();j++){
					if(Math.random()>0.70){
						ProductItem productItem=ProductItem.builder()
								.order(savedOrder)
								.productId(products.get(j).getId())
								.price(products.get(j).getPrice())
								.quantity(1+random.nextInt(100))
								.discount(Math.random())
								.build();
						productItemRepo.save(productItem);
					}
				}
			}
 		};
	}
}