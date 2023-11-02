package bsuir.korotkov.onlinestore.repositories;

import bsuir.korotkov.onlinestore.models.Appliance;
import bsuir.korotkov.onlinestore.models.Cart;
import bsuir.korotkov.onlinestore.models.CartAppliances;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartAppliancesRepository extends JpaRepository<CartAppliances, Integer> {
    List<CartAppliances> findAllByCartAppliancesCart(Cart cart);
    Optional<CartAppliances> findCartAppliancesByCartAppliancesCartAndCartAppliancesAppliance(Cart cart, Appliance appliance);
}
