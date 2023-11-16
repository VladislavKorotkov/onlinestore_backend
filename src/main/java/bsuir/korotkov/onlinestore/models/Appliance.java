package bsuir.korotkov.onlinestore.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "appliance")
public class Appliance {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Название товара не может быть пустым")
    private String name;

    @Column(name = "price")
    @NotNull(message = "Цена не может быть пустой")
    private int price;

    @Column(name = "count")
    @NotNull(message = "Количество не может быть пустым")
    private int count;

    @Column(name = "img")
    @NotEmpty(message = "Картинка не может отсутсвовать")
    private String img;

    @Column(name = "rating")
    private int rating;


    @Column(name = "description")
    @NotEmpty(message = "Описание не может быть пустым")
    private String description;

    @ManyToOne()
    @JoinColumn(name="type_id", referencedColumnName = "id")
    @JsonIgnore
    private Type typeApl;

    @ManyToOne()
    @JoinColumn(name="brand_id", referencedColumnName = "id")
    @JsonIgnore
    private Brand brandApl;

    @OneToMany(mappedBy = "appliance_ord_apl")
    @JsonIgnore
    private List<OrderAppliances> order_appliances;

    @OneToMany(mappedBy = "cartAppliancesAppliance")
    @JsonIgnore
    private List<CartAppliances> cart_appliances;

    @OneToMany(mappedBy = "applianceRating")
    @JsonIgnore
    private List<Rating> ratings;

    public Appliance(String name, int price, String img, String description, int count) {
        this.name = name;
        this.price = price;
        this.img = img;
        this.description = description;
        this.count = count;
    }

    public Appliance() {
    }

    public Appliance(int id, String name, int price, String img) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.img = img;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTypeApl(Type type_apl) {
        this.typeApl = type_apl;
    }

    public void setBrandApl(Brand brand_apl) {
        this.brandApl = brand_apl;
    }

    public void setOrder_appliances(List<OrderAppliances> order_appliances) {
        this.order_appliances = order_appliances;
    }

    public void setCart_appliances(List<CartAppliances> cart_appliances) {
        this.cart_appliances = cart_appliances;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImg() {
        return img;
    }

    public String getDescription() {
        return description;
    }

    public Type getTypeApl() {
        return typeApl;
    }

    public Brand getBrandApl() {
        return brandApl;
    }

    public List<OrderAppliances> getOrder_appliances() {
        return order_appliances;
    }

    public List<CartAppliances> getCart_appliances() {
        return cart_appliances;
    }

    public List<Rating> getRatings() {
        return ratings;
    }


    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
