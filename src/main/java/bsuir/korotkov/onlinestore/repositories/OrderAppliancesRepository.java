package bsuir.korotkov.onlinestore.repositories;

import bsuir.korotkov.onlinestore.models.Order;
import bsuir.korotkov.onlinestore.models.OrderAppliances;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderAppliancesRepository extends JpaRepository<OrderAppliances, Integer> {

}
