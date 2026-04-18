package net.anas.billing_service.clients;

import net.anas.billing_service.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service")
public interface CustomerRestClient {
    @GetMapping(path = "/customers/{id}?projection=fullCustomer")
    public Customer findCustomerById(@PathVariable Long id);
}
