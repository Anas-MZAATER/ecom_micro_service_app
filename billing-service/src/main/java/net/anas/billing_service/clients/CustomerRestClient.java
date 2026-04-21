package net.anas.billing_service.clients;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import net.anas.billing_service.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "customer-service")
public interface CustomerRestClient {
    @GetMapping(path = "/customers/{id}?projection=fullCustomer")
    @CircuitBreaker(name = "customerServiceCB", fallbackMethod = "getDefaultCustomer")
    public Customer findCustomerById(@PathVariable Long id);

    @GetMapping(path = "/customers?projection=fullCustomer")
    @CircuitBreaker(name = "customerServiceCB", fallbackMethod = "getDefaultCustomers")
    public PagedModel<Customer> getAllCustomers();


    default Customer getDefaultCustomer(Long id, Exception exception){
        return Customer.builder()
                .id(id)
                .name("default Name")
                .email("default Email")
                .build();
    }

    default PagedModel<Customer> getDefaultCustomers(Exception exception){
        return PagedModel.empty();
    }
}
