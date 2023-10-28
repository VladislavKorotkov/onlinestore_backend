package bsuir.korotkov.onlinestore.repositories;

import bsuir.korotkov.onlinestore.models.Appliance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplianceRepository extends JpaRepository<Appliance, Integer> {
}
