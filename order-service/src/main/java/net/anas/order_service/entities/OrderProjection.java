package net.anas.order_service.entities;

import net.anas.order_service.enums.OrderStatus;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDate;

@Projection(name = "fullOrder",types = {Order.class})
public interface OrderProjection {
    Long getId();
    LocalDate getCreatedAt();
    Long getCustomerId();
    OrderStatus getStatus();
}
