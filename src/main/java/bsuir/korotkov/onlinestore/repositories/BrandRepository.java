package bsuir.korotkov.onlinestore.repositories;

import bsuir.korotkov.onlinestore.models.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Integer> {
    Optional<Brand> findByName(String s);
}
