package bsuir.korotkov.onlinestore.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account_cart;

    @OneToMany(mappedBy = "cart_cart_apl")
    private List<CartAppliances> cart_appliances;

    public int getId() {
        return id;
    }

    public Account getAccount_cart() {
        return account_cart;
    }

    public List<CartAppliances> getCart_appliances() {
        return cart_appliances;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAccount_cart(Account account_cart) {
        this.account_cart = account_cart;
    }

    public void setCart_appliances(List<CartAppliances> cart_appliances) {
        this.cart_appliances = cart_appliances;
    }

    public Cart(int id) {
        this.id = id;
    }

    public Cart() {
    }

}
