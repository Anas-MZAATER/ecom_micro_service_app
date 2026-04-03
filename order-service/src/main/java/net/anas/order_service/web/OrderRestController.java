package net.anas.order_service.web;

import net.anas.order_service.modal.Customer;
import net.anas.order_service.modal.Product;
import net.anas.order_service.entities.Order;
import net.anas.order_service.repo.OrderRepo;
import net.anas.order_service.repo.ProductItemRepo;
import net.anas.order_service.services.CustomerRestClientService;
import net.anas.order_service.services.InventoryRestClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderRestController {
    private OrderRepo orderRepo;
    private ProductItemRepo productItemRepo;
    private CustomerRestClientService customerRestClientService;
    private InventoryRestClientService inventoryRestClientService;

    public OrderRestController(OrderRepo orderRepo, ProductItemRepo productItemRepo,
                               CustomerRestClientService customerRestClientService,
                               InventoryRestClientService inventoryRestClientService) {
        this.orderRepo = orderRepo;
        this.productItemRepo = productItemRepo;
        this.customerRestClientService = customerRestClientService;
        this.inventoryRestClientService = inventoryRestClientService;
    }

    @GetMapping("/fullOrder/{id}")
    public Order getOrder(@PathVariable Long id) {
        Order order = orderRepo.findById(id).orElseThrow();
        Customer customer = customerRestClientService.customerById(order.getCustomerId());
        order.setCustomer(customer);
        order.getProductItems().forEach(pi->{
            Product product=inventoryRestClientService.productById(pi.getProductId());
            pi.setProduct(product);
        });
        return order;
    }
}
