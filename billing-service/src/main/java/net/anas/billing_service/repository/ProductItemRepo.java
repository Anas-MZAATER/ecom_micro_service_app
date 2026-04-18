package net.anas.billing_service.repository;

import net.anas.billing_service.entities.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductItemRepo extends JpaRepository<ProductItem,Long> {
}
