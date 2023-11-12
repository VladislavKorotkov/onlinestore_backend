package bsuir.korotkov.onlinestore.services;

import bsuir.korotkov.onlinestore.dto.CartDTORequest;
import bsuir.korotkov.onlinestore.dto.CartDTOResponse;
import bsuir.korotkov.onlinestore.models.Account;
import bsuir.korotkov.onlinestore.models.Appliance;
import bsuir.korotkov.onlinestore.models.Cart;
import bsuir.korotkov.onlinestore.models.CartAppliances;
import bsuir.korotkov.onlinestore.repositories.ApplianceRepository;
import bsuir.korotkov.onlinestore.repositories.CartAppliancesRepository;
import bsuir.korotkov.onlinestore.repositories.CartRepository;
import bsuir.korotkov.onlinestore.util.AccessException;
import bsuir.korotkov.onlinestore.util.ObjectIsPresentException;
import bsuir.korotkov.onlinestore.util.ObjectNotCreatedException;
import bsuir.korotkov.onlinestore.util.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private  final CartAppliancesRepository cartAppliancesRepository;
    private final ApplianceRepository applianceRepository;

    public CartService(CartRepository cartRepository, CartAppliancesRepository cartAppliancesRepository, ApplianceRepository applianceRepository) {
        this.cartRepository = cartRepository;
        this.cartAppliancesRepository = cartAppliancesRepository;
        this.applianceRepository = applianceRepository;
    }

    @Transactional
    public void addAppliance(Account account, CartDTORequest cartDTORequest) throws ObjectNotFoundException, ObjectIsPresentException {
        Optional<Appliance> appliance = applianceRepository.findById(cartDTORequest.getAppliance_id());
        Optional<CartAppliances> cartAppliances = cartAppliancesRepository.findCartAppliancesByCartAppliancesCartAndCartAppliancesAppliance(account.getCart(), appliance.get());
        if(cartAppliances.isPresent()){
            throw new ObjectIsPresentException("Товар уже добавлен в корзину");
        }
        if(appliance.isEmpty()){
           throw new ObjectNotFoundException();
        }
        CartAppliances new_cartAppliances = new CartAppliances();
        new_cartAppliances.setCartAppliancesCart(account.getCart());
        new_cartAppliances.setCartAppliancesAppliance(appliance.get());
        new_cartAppliances.setCount(cartDTORequest.getCount());
        cartAppliancesRepository.save(new_cartAppliances);
    }

    @Transactional
    public void updateAppliance(Account account, int id, int count) throws ObjectNotFoundException, AccessException, ObjectNotCreatedException {

        Optional<CartAppliances> cartAppliances = cartAppliancesRepository.findById(id);
        if(cartAppliances.isEmpty()){
            throw new ObjectNotFoundException("Товар в корзине не найден");
        }
        if(!cartAppliances.get().getCartAppliancesCart().equals(account.getCart())){
            throw new AccessException("Невозможно изменить чужие товары из корзины");
        }
        if(count>cartAppliances.get().getCartAppliancesAppliance().getCount()){
            throw new ObjectNotCreatedException("Данное количество товара отсутствует");
        }
        CartAppliances cartAppliance = cartAppliances.get();
        cartAppliance.setCount(count);
        cartAppliancesRepository.save(cartAppliance);
    }

    @Transactional
    public void deleteAppliance(Account account, int id) throws AccessException, ObjectNotFoundException {
        Optional<CartAppliances> cartAppliances = cartAppliancesRepository.findById(id);
        if(cartAppliances.isEmpty()){
            throw new ObjectNotFoundException("Товар в корзине не найден");
        }
        if(!cartAppliances.get().getCartAppliancesCart().equals(account.getCart())){
            throw new AccessException("Невозможно удалить чужие товары из корзины");
        }
        cartAppliancesRepository.delete(cartAppliances.get());
    }

    public List<CartDTOResponse> getAllAppliances(Account account){
        List<CartAppliances> cartAppliances = cartAppliancesRepository.findAllByCartAppliancesCart(account.getCart());
        List<CartDTOResponse> cartDTOResponses = new ArrayList<>();
        for(CartAppliances cartAppliance: cartAppliances){
            CartDTOResponse cartDTOResponse = new CartDTOResponse(cartAppliance.getId(),
                    cartAppliance.getCartAppliancesAppliance().getId(),
                    cartAppliance.getCartAppliancesAppliance().getImg(),
                    cartAppliance.getCartAppliancesAppliance().getName(),
                    cartAppliance.getCount(),
                    cartAppliance.getCartAppliancesAppliance().getCount(),
                    cartAppliance.getCartAppliancesAppliance().getPrice());
            cartDTOResponses.add(cartDTOResponse);
        }
        for(CartDTOResponse cartDTOResponse: cartDTOResponses){
            System.out.println(cartDTOResponse.getAppliance_id());
        }
        return cartDTOResponses;
    }
}
