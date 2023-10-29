package bsuir.korotkov.onlinestore.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "cart_appliances")
public class CartAppliances {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "count")
    @NotNull(message = "Количество не может быть пустым")
    private int count;

    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart_cart_apl;

    @ManyToOne
    @JoinColumn(name = "appliance_id", referencedColumnName = "id")
    private Appliance appliance_cart_apl;

    public int getCount() {
        return count;
    }

    public CartAppliances(int id, int count) {
        this.id = id;
        this.count = count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCart_cart_apl(Cart cart_cart_apl) {
        this.cart_cart_apl = cart_cart_apl;
    }

    public void setAppliance_cart_apl(Appliance appliance_cart_apl) {
        this.appliance_cart_apl = appliance_cart_apl;
    }

    public int getId() {
        return id;
    }

    public Cart getCart_cart_apl() {
        return cart_cart_apl;
    }

    public Appliance getAppliance_cart_apl() {
        return appliance_cart_apl;
    }

    public CartAppliances() {
    }

    public CartAppliances(int count) {
        this.count = count;
    }
}
