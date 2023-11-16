package bsuir.korotkov.onlinestore.services;


import bsuir.korotkov.onlinestore.models.Appliance;
import bsuir.korotkov.onlinestore.models.Type;
import bsuir.korotkov.onlinestore.repositories.ApplianceRepository;
import bsuir.korotkov.onlinestore.repositories.TypeRepository;
import bsuir.korotkov.onlinestore.util.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeService {
    private TypeRepository typeRepository;

    private ApplianceRepository applianceRepository;

    public TypeService(TypeRepository typeRepository, ApplianceRepository applianceRepository) {
        this.typeRepository = typeRepository;
        this.applianceRepository = applianceRepository;
    }

    @Transactional
    public void createType(Type type){
        typeRepository.save(type);
    }

    public List<Type> getAllTypes(){
        return typeRepository.findAll();
    }

    public Type loadTypeByName(String s) throws ObjectNotFoundException {
        Optional<Type> type  = typeRepository.findByName(s);
        return type.orElseThrow(ObjectNotFoundException::new);
    }

    public Type loadTypeById(int id) throws ObjectNotFoundException {
        Optional<Type> type  = typeRepository.findById(id);
        return type.orElseThrow(ObjectNotFoundException::new);
    }

    @Transactional
    public void deleteType(int id) throws ObjectNotFoundException{
        Optional<Type> type = typeRepository.findById(id);
        if(type.isEmpty()){
            throw new ObjectNotFoundException("Товар не найден");
        }
        List<Appliance> applianceList = applianceRepository.findAllByTypeApl(type.get());
        if(!applianceList.isEmpty()){
            throw new ObjectNotFoundException("Невозможно удалить тип. Удалите соответствующие товары");
        }
        typeRepository.delete(type.get());
    }

    @Transactional
    public void updateType(int id, Type type) throws ObjectNotFoundException {
        Optional<Type> type_old_optional = typeRepository.findById(id);
        if(type_old_optional.isEmpty()){
            throw new ObjectNotFoundException();
        }
        Type type_old = type_old_optional.get();
        type_old.setName(type.getName());
        typeRepository.save(type_old);
    }
}
