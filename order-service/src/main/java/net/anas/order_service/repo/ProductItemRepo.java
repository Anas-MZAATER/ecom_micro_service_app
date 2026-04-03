package net.anas.order_service.repo;

import net.anas.order_service.entities.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProductItemRepo extends JpaRepository<ProductItem, Long> {
}
