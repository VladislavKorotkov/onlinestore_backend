package bsuir.korotkov.onlinestore.services;

import bsuir.korotkov.onlinestore.models.Account;
import bsuir.korotkov.onlinestore.models.Brand;
import bsuir.korotkov.onlinestore.repositories.BrandRepository;
import bsuir.korotkov.onlinestore.security.AccountDetails;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService {
    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
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
}
