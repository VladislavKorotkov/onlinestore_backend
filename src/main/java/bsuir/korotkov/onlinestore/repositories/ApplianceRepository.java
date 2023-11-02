package bsuir.korotkov.onlinestore.repositories;

import bsuir.korotkov.onlinestore.models.Appliance;
import bsuir.korotkov.onlinestore.models.Brand;
import bsuir.korotkov.onlinestore.models.Type;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplianceRepository extends JpaRepository<Appliance, Integer> {
    List<Appliance> findAllByBrandAplAndTypeApl(Brand brand, Type type);
    List<Appliance> findAllByBrandApl(Brand brand);
    List<Appliance> findAllByTypeApl(Type type);
}
