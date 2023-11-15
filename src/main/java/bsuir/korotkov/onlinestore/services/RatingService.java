package bsuir.korotkov.onlinestore.services;

import bsuir.korotkov.onlinestore.dto.RatingDTO;
import bsuir.korotkov.onlinestore.models.Account;
import bsuir.korotkov.onlinestore.repositories.ApplianceRepository;
import bsuir.korotkov.onlinestore.repositories.OrderAppliancesRepository;
import bsuir.korotkov.onlinestore.repositories.RatingRepository;
import org.springframework.stereotype.Service;

@Service
public class RatingService {
    private final RatingRepository ratingRepository;

    private final ApplianceRepository applianceRepository;
    private final OrderAppliancesRepository orderAppliancesRepository;

    public RatingService(RatingRepository ratingRepository, ApplianceRepository applianceRepository, OrderAppliancesRepository orderAppliancesRepository) {
        this.ratingRepository = ratingRepository;
        this.applianceRepository = applianceRepository;
        this.orderAppliancesRepository = orderAppliancesRepository;
    }

    public void addRating(RatingDTO ratingDTO, Account account){

    }
}
