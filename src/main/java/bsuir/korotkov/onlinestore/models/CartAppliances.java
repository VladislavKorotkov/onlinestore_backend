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
    private Cart cartAppliancesCart;

    @ManyToOne
    @JoinColumn(name = "appliance_id", referencedColumnName = "id")
    private Appliance cartAppliancesAppliance;

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

    public void setCartAppliancesCart(Cart cart_cart_apl) {
        this.cartAppliancesCart = cart_cart_apl;
    }

    public void setCartAppliancesAppliance(Appliance appliance_cart_apl) {
        this.cartAppliancesAppliance = appliance_cart_apl;
    }

    public int getId() {
        return id;
    }

    public Cart getCartAppliancesCart() {
        return cartAppliancesCart;
    }

    public Appliance getCartAppliancesAppliance() {
        return cartAppliancesAppliance;
    }

    public CartAppliances() {
    }

    public CartAppliances(int count) {
        this.count = count;
    }
}
