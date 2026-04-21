package net.anas.order_service.services;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import net.anas.order_service.modal.Customer;
import net.anas.order_service.myConfig.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "customer-service", configuration = FeignConfig.class)
public interface CustomerRestClientService {
    @GetMapping(path = "/customers/{id}?projection=fullCustomer")
    @CircuitBreaker(name = "customerServiceCB", fallbackMethod = "getDefaultCustomer")
    public Customer customerById(@PathVariable Long id);

    @GetMapping(path = "/customers?projection=fullCustomer")
    @CircuitBreaker(name = "customerServiceCB", fallbackMethod = "getDefaultCustomers")
    public PagedModel<Customer> allCustomers();


    default Customer getDefaultCustomer(Long id, Exception exception){
        Customer defaultCustomer=new Customer();
        defaultCustomer.setId(id);
        defaultCustomer.setName("Name not Available");
        defaultCustomer.setEmail("Email not Available");
        return defaultCustomer;
    }

    default PagedModel<Customer> getDefaultCustomers(Exception exception){
        return PagedModel.empty();
    }
}
