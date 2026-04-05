package net.anas.customer_service.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RefreshScope
public class CustomerConfigTestController {
    @Value("${global.params.p1:0}")
    private Integer p1;
    @Value("${global.params.p2:0}")
    private Integer p2;
    @Value("${customer.params.x:0}")
    private Integer x;
    @Value("${customer.params.y:0}")
    private Integer y;

    @GetMapping("/params")
    public Map<String, Integer> getCustomerConfig() {
        return Map.of("p1", p1, "p2", p2, "x", x, "y", y);
    }
}
