package bsuir.korotkov.onlinestore.services;

import bsuir.korotkov.onlinestore.dto.ApplianceDTO;
import bsuir.korotkov.onlinestore.repositories.ApplianceRepository;
import jakarta.transaction.Transactional;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ApplianceService {
    private final ApplianceRepository applianceRepository;

    public ApplianceService(ApplianceRepository applianceRepository, ResourceLoader resourceLoader) {
        this.applianceRepository = applianceRepository;
    }

     @Transactional
    public void create(ApplianceDTO applianceDTO) throws IOException {
             byte[] bytes = applianceDTO.getImg().getBytes();
             Path path = Paths.get("src/main/resources/static/" + UUID.randomUUID().toString() + ".jpg");
             Files.write(path, bytes);

     }
}

