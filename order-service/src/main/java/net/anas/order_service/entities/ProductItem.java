package net.anas.order_service.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import net.anas.order_service.modal.Product;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @Builder
public class ProductItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    @Transient
    private Product product;
    private Double price;
    private Integer quantity;
    private Double discount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Order order;
}
