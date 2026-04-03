package net.anas.order_service.entities;

import jakarta.persistence.*;
import lombok.*;
import net.anas.order_service.enums.OrderStatus;
import net.anas.order_service.modal.Customer;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @Builder
@Table(name = "ordres")
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate createdAt;
    private OrderStatus status;
    private Long customerId;
    @Transient
    private Customer customer;
    @OneToMany(mappedBy = "order")
    private List<ProductItem> productItems;
}
