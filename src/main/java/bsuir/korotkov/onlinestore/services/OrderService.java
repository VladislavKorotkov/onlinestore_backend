package bsuir.korotkov.onlinestore.services;

import bsuir.korotkov.onlinestore.dto.OrderDTO;
import bsuir.korotkov.onlinestore.models.Account;
import bsuir.korotkov.onlinestore.models.Address;
import bsuir.korotkov.onlinestore.models.Appliance;
import bsuir.korotkov.onlinestore.models.CartAppliances;
import bsuir.korotkov.onlinestore.models.Order;
import bsuir.korotkov.onlinestore.models.OrderAppliances;
import bsuir.korotkov.onlinestore.repositories.AddressRepository;
import bsuir.korotkov.onlinestore.repositories.ApplianceRepository;
import bsuir.korotkov.onlinestore.repositories.CartAppliancesRepository;
import bsuir.korotkov.onlinestore.repositories.CartRepository;
import bsuir.korotkov.onlinestore.repositories.OrderAppliancesRepository;
import bsuir.korotkov.onlinestore.repositories.OrderRepository;
import bsuir.korotkov.onlinestore.util.AccessException;
import bsuir.korotkov.onlinestore.util.ObjectNotCreatedException;
import bsuir.korotkov.onlinestore.util.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderAppliancesRepository orderAppliancesRepository;
    private final CartRepository cartRepository;
    private final CartAppliancesRepository cartAppliancesRepository;
    private final ApplianceRepository applianceRepository;
    private final AddressRepository addressRepository;

    public OrderService(OrderRepository orderRepository, OrderAppliancesRepository orderAppliancesRepository, CartRepository cartRepository, CartAppliancesRepository cartAppliancesRepository, ApplianceRepository applianceRepository, AddressRepository addressRepository) {
        this.orderRepository = orderRepository;
        this.orderAppliancesRepository = orderAppliancesRepository;
        this.cartRepository = cartRepository;
        this.cartAppliancesRepository = cartAppliancesRepository;
        this.applianceRepository = applianceRepository;
        this.addressRepository = addressRepository;
    }

    public Order addOrder(OrderDTO orderDTO, Account account) throws ObjectNotCreatedException, ObjectNotFoundException {
        List<CartAppliances> cartAppliancesList = account.getCart().getCart_appliances();
        Optional<Address> address = addressRepository.findById(orderDTO.getAddress_id());
        if(address.isEmpty()){
            throw new ObjectNotFoundException("Адрес не найден");
        }
        if(cartAppliancesList.isEmpty()){
            throw new ObjectNotCreatedException("Корзина пуста");
        }
        boolean is_notCorrect = cartAppliancesList.stream().anyMatch(cartAppliance -> cartAppliance.getCount() > cartAppliance.getCartAppliancesAppliance().getCount());
        if(is_notCorrect){
            throw new ObjectNotCreatedException("Выбрано недоступное количество товаров");
        }
        int full_cost = cartAppliancesList.stream().map(cartAppliance -> cartAppliance.getCount()*cartAppliance.getCartAppliancesAppliance().getPrice()).reduce(0, Integer::sum);
        Order order = new Order(full_cost, orderDTO.isIs_paid(), false, LocalDateTime.now(), address.get(), account);
        orderRepository.save(order);
        for(CartAppliances cartAppliances: cartAppliancesList){
            Appliance appliance = cartAppliances.getCartAppliancesAppliance();
            appliance.setCount(appliance.getCount()-cartAppliances.getCount());
            OrderAppliances orderAppliances = new OrderAppliances(cartAppliances.getCount(), cartAppliances.getCartAppliancesAppliance().getPrice(), order, cartAppliances.getCartAppliancesAppliance());
            orderAppliancesRepository.save(orderAppliances);
            cartAppliancesRepository.delete(cartAppliances);
            applianceRepository.save(appliance);
        }
        return order;
    }
    public List<Order> getAllOrdersForUser(Account account) throws ObjectNotFoundException {
        List<Order> orderList = orderRepository.findAllByAccountOrder(account);
        return orderList;
    }

    public List<Order> getAllOrdersForAdmin(){
        List<Order> orderList = orderRepository.findAll();
        return orderList;
    }

    public Order getOneOrderById(Account account, int id) throws ObjectNotFoundException, AccessException {
        Optional<Order> order_opt = orderRepository.findById(id);
        if(order_opt.isEmpty()){
            throw new ObjectNotFoundException("Заказ не найден");
        }
        Order order = order_opt.get();
        if(account.getRole().equals("ROLE_ADMIN")){
            return order;
        }
        else if(order.getAccountOrder().equals(account)){
            return order;
        }
        throw new AccessException("Доступ запрещен");

    }

    public void updateOrderToDelivered(int id) throws ObjectNotFoundException, ObjectNotCreatedException {
        Optional<Order> order_opt = orderRepository.findById(id);
        if(order_opt.isEmpty()){
            throw new ObjectNotFoundException("Заказ не найден");
        }
        Order order = order_opt.get();
        if(order.isIs_delivered()){
            throw new ObjectNotCreatedException("Заказ уже доставлен");
        }
        order.setIs_delivered(true);
        orderRepository.save(order);
    }

    public void deleteOrder(int id) throws ObjectNotFoundException {
        Optional<Order> order_opt = orderRepository.findById(id);
        if(order_opt.isEmpty()){
            throw new ObjectNotFoundException("Заказ не найден");
        }
        Order order = order_opt.get();
        orderRepository.delete(order);
    }
}
