package bsuir.korotkov.onlinestore.services;

import bsuir.korotkov.onlinestore.dto.ApplianceDTORequest;
import bsuir.korotkov.onlinestore.dto.ApplianceDTOResponse;
import bsuir.korotkov.onlinestore.models.Appliance;
import bsuir.korotkov.onlinestore.models.Brand;
import bsuir.korotkov.onlinestore.models.Type;
import bsuir.korotkov.onlinestore.repositories.ApplianceRepository;
import bsuir.korotkov.onlinestore.repositories.BrandRepository;
import bsuir.korotkov.onlinestore.repositories.TypeRepository;
import bsuir.korotkov.onlinestore.util.ObjectNotCreatedException;
import bsuir.korotkov.onlinestore.util.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ApplianceService {
    private final ApplianceRepository applianceRepository;

    private final TypeRepository typeRepository;

    private final BrandRepository brandRepository;

    public ApplianceService(ApplianceRepository applianceRepository, ResourceLoader resourceLoader, TypeRepository typeRepository, BrandRepository brandRepository) {
        this.applianceRepository = applianceRepository;
        this.typeRepository = typeRepository;
        this.brandRepository = brandRepository;
    }

     @Transactional
    public void createAppliance(ApplianceDTORequest applianceDTO) throws IOException, ObjectNotCreatedException {
         byte[] bytes = applianceDTO.getImg().getBytes();
         String name_img = UUID.randomUUID().toString() + ".jpg";
         Path path = Paths.get("src/main/resources/static/" + name_img);
         Files.write(path, bytes);
         Appliance appliance = new Appliance(applianceDTO.getName(), applianceDTO.getPrice(), name_img, applianceDTO.getDescription(), applianceDTO.getCount());
         Optional<Type> type = typeRepository.findById(applianceDTO.getType());
         Optional<Brand> brand = brandRepository.findById(applianceDTO.getBrand());
         if(brand.isEmpty() || type.isEmpty()){
             throw new ObjectNotCreatedException("Бренд или товар не найден");
         }
         appliance.setBrand_apl(brand.get());
         appliance.setType_apl(type.get());
         applianceRepository.save(appliance);
     }

     public List<ApplianceDTOResponse> getAllAppliances(){
        List<Appliance> appliances = applianceRepository.findAll();
        return convertAppliancesToAppliancesDTOResponse(appliances);
     }

     public ApplianceDTOResponse loadApplianceById(int id) throws ObjectNotFoundException {
        Optional<Appliance> appliance = applianceRepository.findById(id);
        if(appliance.isEmpty()){
            throw new ObjectNotFoundException();
        }
        return convertApplianceToApplianceDTOResponse(appliance.get());
     }

     @Transactional
     public void deleteAppliance(int id) throws ObjectNotFoundException {
         Optional<Appliance> appliance = applianceRepository.findById(id);
         if(appliance.isEmpty()){
             throw new ObjectNotFoundException();
         }
         applianceRepository.delete(appliance.get());
     }

     @Transactional
     public void updateAppliance(int id, ApplianceDTORequest applianceDTORequest) throws ObjectNotFoundException {
        /* Optional<Appliance> appliance_old_optional = applianceRepository.findById(id);
         if(appliance_old_optional.isEmpty()){
             throw new ObjectNotFoundException();
         }
         Appliance appliance_old = appliance_old_optional.get();
         appliance_old.setName(appliance.getName());
         applianceRepository.save(appliance_old);

         byte[] bytes = applianceDTO.getImg().getBytes();
         String name_img = UUID.randomUUID().toString() + ".jpg";
         Path path = Paths.get("src/main/resources/static/" + name_img);
         Files.write(path, bytes);
         Appliance appliance = new Appliance(applianceDTO.getName(), applianceDTO.getPrice(), name_img, applianceDTO.getDescription(), applianceDTO.getCount());
         Optional<Type> type = typeRepository.findById(applianceDTO.getType());
         Optional<Brand> brand = brandRepository.findById(applianceDTO.getBrand());
         if(brand.isEmpty() || type.isEmpty()){
             throw new ObjectNotCreatedException("Бренд или товар не найден");
         }
         appliance.setBrand_apl(brand.get());
         appliance.setType_apl(type.get());
         applianceRepository.save(appliance); */
     }

     private List<ApplianceDTOResponse> convertAppliancesToAppliancesDTOResponse(List<Appliance> appliances){
        return appliances.stream().map(appliance -> convertApplianceToApplianceDTOResponse(appliance)).collect(Collectors.toList());
    }

     private ApplianceDTOResponse convertApplianceToApplianceDTOResponse(Appliance appliance){
        ApplianceDTOResponse applianceDTOResponse = new ApplianceDTOResponse();
        applianceDTOResponse.setBrand(appliance.getBrand_apl().getId());
        applianceDTOResponse.setBrand_name(appliance.getBrand_apl().getName());
        applianceDTOResponse.setType(appliance.getType_apl().getId());
        applianceDTOResponse.setType_name(appliance.getType_apl().getName());
        applianceDTOResponse.setCount(appliance.getCount());
        applianceDTOResponse.setDescription(appliance.getDescription());
        applianceDTOResponse.setImg(appliance.getImg());
        applianceDTOResponse.setName(appliance.getName());
        applianceDTOResponse.setPrice(appliance.getPrice());
        return applianceDTOResponse;
     }
}

