package bsuir.korotkov.onlinestore.repositories;

import bsuir.korotkov.onlinestore.models.OrderAppliances;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderAppliancesRepository extends JpaRepository<OrderAppliances, Integer> {
}
