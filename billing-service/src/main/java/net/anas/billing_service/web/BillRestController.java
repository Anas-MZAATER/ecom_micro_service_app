package net.anas.billing_service.web;

import net.anas.billing_service.clients.CustomerRestClient;
import net.anas.billing_service.clients.ProductRestClient;
import net.anas.billing_service.entities.Bill;
import net.anas.billing_service.repository.BillRepo;
import net.anas.billing_service.repository.ProductItemRepo;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillRestController {
    private BillRepo billRepo;
    private ProductItemRepo productItemRepo;
    private CustomerRestClient customerRestClient;
    private ProductRestClient productRestClient;

    public BillRestController(BillRepo billRepo, ProductItemRepo productItemRepo,
                              CustomerRestClient customerRestClient,
                              ProductRestClient productRestClient) {
        this.billRepo = billRepo;
        this.productItemRepo = productItemRepo;
        this.customerRestClient = customerRestClient;
        this.productRestClient = productRestClient;
    }

    @GetMapping(path = "fullBill/{id}")
    public Bill bill(@PathVariable Long id) {
        Bill bill = billRepo.findById(id).get();
        bill.setCustomer(customerRestClient.findCustomerById(bill.getCustomerId()));
        bill.getProductItems().forEach(pi -> {
            pi.setProduct(productRestClient.findProductById(pi.getProductId()));
        });
        return bill;
    }
}
