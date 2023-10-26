package bsuir.korotkov.onlinestore.repositories;

import bsuir.korotkov.onlinestore.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByUsername(String username);
}
