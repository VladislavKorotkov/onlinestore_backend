package bsuir.korotkov.onlinestore.repositories;

import bsuir.korotkov.onlinestore.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
}
