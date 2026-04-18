package net.anas.billing_service.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Product {
    private Long id;
    private String name;
    private Double price;
    private Integer quantity;
}
