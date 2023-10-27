package bsuir.korotkov.onlinestore.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Entity
@Table(name = "type")
public class Type {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public Type() {
    }

    @Column(name = "name")
    @NotEmpty(message="Название не может быть пустым")
    private String name;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAppliances(List<Appliance> appliances) {
        this.appliances = appliances;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Appliance> getAppliances() {
        return appliances;
    }

    public Type(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @OneToMany(mappedBy = "type_apl")
    List<Appliance> appliances;
}
