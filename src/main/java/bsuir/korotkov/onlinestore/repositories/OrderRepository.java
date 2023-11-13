package bsuir.korotkov.onlinestore.repositories;

import bsuir.korotkov.onlinestore.models.Account;
import bsuir.korotkov.onlinestore.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    public List<Order> findAllByAccountOrder(Account account);
}
