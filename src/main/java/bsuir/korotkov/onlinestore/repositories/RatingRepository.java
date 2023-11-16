package bsuir.korotkov.onlinestore.repositories;

import bsuir.korotkov.onlinestore.models.Account;
import bsuir.korotkov.onlinestore.models.Appliance;
import bsuir.korotkov.onlinestore.models.Rating;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
    List<Rating> findAllByApplianceRating(Appliance appliance);

    List<Rating> findAllByApplianceRatingAndAccountRating(Appliance appliance, Account account);
}
