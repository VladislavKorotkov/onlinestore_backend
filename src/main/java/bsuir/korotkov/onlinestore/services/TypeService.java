package bsuir.korotkov.onlinestore.services;


import bsuir.korotkov.onlinestore.models.Type;
import bsuir.korotkov.onlinestore.repositories.TypeRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeService {
    private TypeRepository typeRepository;

    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Transactional
    public void createType(Type type){
        typeRepository.save(type);
    }

    public List<Type> getAllTypes(){
        return typeRepository.findAll();
    }
    public Type loadTypeByName(String s) throws UsernameNotFoundException {
        Optional<Type> type  = typeRepository.findByName(s);

        if (type.isEmpty())
            throw new UsernameNotFoundException("Данный тип не найден");

        return type.get();
    }
}
