package ua.com.diplomka.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.diplomka.orderservice.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
