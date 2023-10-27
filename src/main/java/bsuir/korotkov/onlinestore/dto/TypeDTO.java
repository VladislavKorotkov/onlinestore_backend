package bsuir.korotkov.onlinestore.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;

public class TypeDTO {
    @Column(name = "name")
    @NotEmpty(message="Название не может быть пустым")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeDTO() {
    }

    public TypeDTO(String name) {
        this.name = name;
    }
}
