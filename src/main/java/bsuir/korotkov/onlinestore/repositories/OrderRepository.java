package bsuir.korotkov.onlinestore.repositories;

import bsuir.korotkov.onlinestore.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
