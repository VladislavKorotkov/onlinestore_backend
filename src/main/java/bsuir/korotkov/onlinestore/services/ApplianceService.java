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

import java.io.File;
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

    private final FileStorageService fileStorageService;

    public ApplianceService(ApplianceRepository applianceRepository, ResourceLoader resourceLoader, TypeRepository typeRepository, BrandRepository brandRepository, FileStorageService fileStorageService) {
        this.applianceRepository = applianceRepository;
        this.typeRepository = typeRepository;
        this.brandRepository = brandRepository;
        this.fileStorageService = fileStorageService;
    }

     @Transactional
    public void createAppliance(ApplianceDTORequest applianceDTO) throws IOException, ObjectNotCreatedException {
         String name_img = fileStorageService.storeFile(applianceDTO.getImg());
         Appliance appliance = new Appliance(applianceDTO.getName(), applianceDTO.getPrice(), name_img, applianceDTO.getDescription(), applianceDTO.getCount());
         Optional<Type> type = typeRepository.findById(applianceDTO.getType());
         Optional<Brand> brand = brandRepository.findById(applianceDTO.getBrand());
         if(brand.isEmpty() || type.isEmpty()){
             throw new ObjectNotCreatedException("Бренд или товар не найден");
         }
         appliance.setBrandApl(brand.get());
         appliance.setTypeApl(type.get());
         appliance.setRating(5);
         applianceRepository.save(appliance);
     }

     public List<ApplianceDTOResponse> getAllAppliances(){
        List<Appliance> appliances = applianceRepository.findAll();
        return convertAppliancesToAppliancesDTOResponse(appliances);
     }

    public List<ApplianceDTOResponse> getAllAppliancesFilterType(int typeId) throws ObjectNotFoundException {
        Optional<Type> type = typeRepository.findById(typeId);
        if(type.isEmpty()){
            throw new ObjectNotFoundException("Тип не найден");
        }
        List<Appliance> appliances = applianceRepository.findAllByTypeApl(type.get());
        return convertAppliancesToAppliancesDTOResponse(appliances);
    }

    public List<ApplianceDTOResponse> getAllAppliancesFilterBrand(int brandId) throws ObjectNotFoundException {
        Optional<Brand> brand = brandRepository.findById(brandId);
        if(brand.isEmpty()){
            throw new ObjectNotFoundException("Бренд не найден");
        }
        List<Appliance> appliances = applianceRepository.findAllByBrandApl(brand.get());
        return convertAppliancesToAppliancesDTOResponse(appliances);
    }

    public List<ApplianceDTOResponse> getAllAppliancesFilterBrandAndType(int brandId, int typeId) throws ObjectNotFoundException {
        Optional<Brand> brand = brandRepository.findById(brandId);
        Optional<Type> type = typeRepository.findById(typeId);
        if(type.isEmpty()||brand.isEmpty()){
            throw new ObjectNotFoundException("Бренд или тип не найден");
        }
        List<Appliance> appliances = applianceRepository.findAllByBrandAplAndTypeApl(brand.get(), type.get());
        return convertAppliancesToAppliancesDTOResponse(appliances);
    }

     public ApplianceDTOResponse loadApplianceById(int id) throws ObjectNotFoundException {
        Optional<Appliance> appliance = applianceRepository.findById(id);
        if(appliance.isEmpty()){
            throw new ObjectNotFoundException("Товар не найден");
        }
        return convertApplianceToApplianceDTOResponse(appliance.get());
     }

     @Transactional
     public void deleteAppliance(int id) throws ObjectNotFoundException {
         Optional<Appliance> appliance = applianceRepository.findById(id);
         if(appliance.isEmpty()){
             throw new ObjectNotFoundException("Товар не найден");
         }
         fileStorageService.deleteFile(appliance.get().getImg());
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
        if(appliance.getBrandApl()==null){
            applianceDTOResponse.setBrand(-1);
            applianceDTOResponse.setBrand_name("Отсутствует");
        }
        else{
            applianceDTOResponse.setBrand(appliance.getBrandApl().getId());
            applianceDTOResponse.setBrand_name(appliance.getBrandApl().getName());
        }
         if(appliance.getTypeApl()==null){
             applianceDTOResponse.setType(-1);
             applianceDTOResponse.setType_name("Отсутствует");
         }
         else{
             applianceDTOResponse.setType(appliance.getTypeApl().getId());
             applianceDTOResponse.setType_name(appliance.getTypeApl().getName());
         }
        applianceDTOResponse.setCount(appliance.getCount());
        applianceDTOResponse.setDescription(appliance.getDescription());
        applianceDTOResponse.setImg(appliance.getImg());
        applianceDTOResponse.setName(appliance.getName());
        applianceDTOResponse.setPrice(appliance.getPrice());
        applianceDTOResponse.setId(appliance.getId());
        applianceDTOResponse.setRating(appliance.getRating());
        return applianceDTOResponse;
     }
}

