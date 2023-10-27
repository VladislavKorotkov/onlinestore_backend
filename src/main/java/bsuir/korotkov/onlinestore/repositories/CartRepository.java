package bsuir.korotkov.onlinestore.repositories;

import bsuir.korotkov.onlinestore.models.Account;
import bsuir.korotkov.onlinestore.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer>{
}
