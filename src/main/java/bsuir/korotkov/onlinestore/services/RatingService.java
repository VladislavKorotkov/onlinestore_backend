package bsuir.korotkov.onlinestore.services;

import bsuir.korotkov.onlinestore.dto.RatingDTO;
import bsuir.korotkov.onlinestore.models.Account;
import bsuir.korotkov.onlinestore.models.Appliance;
import bsuir.korotkov.onlinestore.models.Order;
import bsuir.korotkov.onlinestore.models.OrderAppliances;
import bsuir.korotkov.onlinestore.models.Rating;
import bsuir.korotkov.onlinestore.repositories.ApplianceRepository;
import bsuir.korotkov.onlinestore.repositories.OrderAppliancesRepository;
import bsuir.korotkov.onlinestore.repositories.OrderRepository;
import bsuir.korotkov.onlinestore.repositories.RatingRepository;
import bsuir.korotkov.onlinestore.util.AccessException;
import bsuir.korotkov.onlinestore.util.ObjectNotCreatedException;
import bsuir.korotkov.onlinestore.util.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {
    private final RatingRepository ratingRepository;

    private final ApplianceRepository applianceRepository;
    private final OrderAppliancesRepository orderAppliancesRepository;
    private final OrderRepository orderRepository;

    public RatingService(RatingRepository ratingRepository, ApplianceRepository applianceRepository, OrderAppliancesRepository orderAppliancesRepository, OrderRepository orderRepository) {
        this.ratingRepository = ratingRepository;
        this.applianceRepository = applianceRepository;
        this.orderAppliancesRepository = orderAppliancesRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void addRating(RatingDTO ratingDTO, Account account) throws ObjectNotFoundException, AccessException, ObjectNotCreatedException {
        Optional<Appliance> appliance_opt = applianceRepository.findById(ratingDTO.getAppliance_id());
        if(appliance_opt.isEmpty()){
            throw new ObjectNotFoundException("Товар не найден");
        }
        Optional<Order> order_opt = orderRepository.findById(ratingDTO.getOrder_id());
        Order order = order_opt.get();
        if(order_opt.isEmpty()){
            throw new ObjectNotFoundException("Заказ не найден");
        }
        if(order.getAccountOrder().getId()!=account.getId()){
            throw new AccessException("Невозможно выставить рейтинг за чужой заказ");
        }
        boolean flag = order.getOrder_appliances().stream().anyMatch(orderAppliances -> orderAppliances.getAppliance_ord_apl().getId()==ratingDTO.getAppliance_id());
        if(!flag){
            throw new ObjectNotCreatedException("Товар отсутствует в заказе");
        }
        List<Rating> rating_list = ratingRepository.findAllByApplianceRatingAndAccountRating(appliance_opt.get(), account);
        if(rating_list.size()!=0){
            throw new ObjectNotCreatedException("Рейтинг для данного товара уже выставлен");
        }
        Rating rating = new Rating(ratingDTO.getRate(), account, appliance_opt.get());
        ratingRepository.save(rating);
        List<Rating> ratingList = ratingRepository.findAllByApplianceRating(appliance_opt.get());
        Integer sum = ratingList.stream()
                .map(x -> x.getRate())
                .reduce(0, Integer::sum);
        Appliance appliance = appliance_opt.get();
        int rate = sum/ratingList.size();
        appliance.setRating(rate);
        applianceRepository.save(appliance);
    }
}
