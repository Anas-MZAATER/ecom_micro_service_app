package net.anas.customer_service;

import net.anas.customer_service.entities.Customer;
import net.anas.customer_service.repo.CustomerRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class CustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(CustomerRepo customerRepo) {
		return args -> {
			customerRepo.saveAll(List.of(
					Customer.builder().name("Anas").email("anas@gmail.com").build(),
					Customer.builder().name("Youssef").email("Youssef@gmail.com").build(),
					Customer.builder().name("Anas").email("anas@gmail.com").build(),
					Customer.builder().name("Aya").email("aya@gmail.com").build()
			));
			customerRepo.findAll().forEach(System.out::println);
		};
	}
}
