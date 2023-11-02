package bsuir.korotkov.onlinestore.services;

import bsuir.korotkov.onlinestore.dto.OrderDTO;
import bsuir.korotkov.onlinestore.models.Account;
import bsuir.korotkov.onlinestore.repositories.OrderAppliancesRepository;
import bsuir.korotkov.onlinestore.repositories.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderAppliancesRepository orderAppliancesRepository;

    public OrderService(OrderRepository orderRepository, OrderAppliancesRepository orderAppliancesRepository) {
        this.orderRepository = orderRepository;
        this.orderAppliancesRepository = orderAppliancesRepository;
    }

    public void addOrder(OrderDTO orderDTO, Account account){

    }
}
