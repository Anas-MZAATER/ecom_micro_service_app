package net.anas.billing_service.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import net.anas.billing_service.model.Product;

@Entity
@NoArgsConstructor
@AllArgsConstructor @Builder @Getter @Setter
public class ProductItem{
@Id @GeneratedValue(strategy= GenerationType.IDENTITY)
private Long id;
private Long productId;
@ManyToOne//(fetch = FetchType.LAZY)
@JsonProperty(access =  JsonProperty.Access.WRITE_ONLY)
private Bill bill;
private Integer quantity;
private Double price;
private Double discount;
@Transient
private Product product;
}