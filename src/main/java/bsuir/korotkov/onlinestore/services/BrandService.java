package bsuir.korotkov.onlinestore.services;

import bsuir.korotkov.onlinestore.models.Appliance;
import bsuir.korotkov.onlinestore.models.Brand;
import bsuir.korotkov.onlinestore.repositories.ApplianceRepository;
import bsuir.korotkov.onlinestore.repositories.BrandRepository;
import bsuir.korotkov.onlinestore.util.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService {
    private final BrandRepository brandRepository;

    private final ApplianceRepository applianceRepository;

    public BrandService(BrandRepository brandRepository, ApplianceRepository applianceRepository) {
        this.brandRepository = brandRepository;
        this.applianceRepository = applianceRepository;
    }

    @Transactional
    public void createBrand(Brand brand){
        brandRepository.save(brand);
    }

    public List<Brand> getAllBrands(){
        return brandRepository.findAll();
    }

    public Brand loadBrandByName(String s) throws UsernameNotFoundException {
        Optional<Brand> brand  = brandRepository.findByName(s);

        if (brand.isEmpty())
            throw new UsernameNotFoundException("Данный бренд не найден");

        return brand.get();
    }
    public Brand loadBrandById(int id) throws ObjectNotFoundException {
        Optional<Brand> brand  = brandRepository.findById(id);
        return brand.orElseThrow(ObjectNotFoundException::new);
    }

    @Transactional
    public void deleteBrand(int id) throws ObjectNotFoundException{
        Optional<Brand> brand = brandRepository.findById(id);
        if(brand.isEmpty()){
            throw new ObjectNotFoundException("Бренд не найден");
        }
        List<Appliance> applianceList = applianceRepository.findAllByBrandApl(brand.get());
        if(!applianceList.isEmpty()){
            throw new ObjectNotFoundException("Невозможно удалить бренд. Удалите соответствующие товары");
        }
        brandRepository.delete(brand.get());
    }

    @Transactional
    public void updateBrand(int id, Brand brand) throws ObjectNotFoundException {
        Optional<Brand> brand_old_optional = brandRepository.findById(id);
        if(brand_old_optional.isEmpty()){
            throw new ObjectNotFoundException();
        }
        Brand brand_old = brand_old_optional.get();
        brand_old.setName(brand.getName());
        brand_old.setCountry(brand.getCountry());
        brandRepository.save(brand_old);
    }
}
