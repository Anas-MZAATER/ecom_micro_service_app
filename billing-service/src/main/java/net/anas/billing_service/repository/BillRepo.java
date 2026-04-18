package net.anas.billing_service.repository;

import net.anas.billing_service.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepo extends JpaRepository<Bill,Long> {
}
