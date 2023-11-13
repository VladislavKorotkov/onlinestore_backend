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
@Table(name = "order_appliances")
public class OrderAppliances {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "count")
    @NotNull(message = "Количество не может быть пустым")
    private int count;

    @Column(name = "price_one")
    @NotNull(message = "Цена не может быть пустой")
    private int price_one;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order_ord_apl;

    @ManyToOne
    @JoinColumn(name = "appliance_id", referencedColumnName = "id")
    private Appliance appliance_ord_apl;

    public void setId(int id) {
        this.id = id;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setPrice_one(int price_one) {
        this.price_one = price_one;
    }

    public void setOrder_ord_apl(Order order_ord_apl) {
        this.order_ord_apl = order_ord_apl;
    }

    public void setAppliance_ord_apl(Appliance appliance_ord_apl) {
        this.appliance_ord_apl = appliance_ord_apl;
    }

    public int getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public int getPrice_one() {
        return price_one;
    }

    public Order getOrder_ord_apl() {
        return order_ord_apl;
    }

    public Appliance getAppliance_ord_apl() {
        return appliance_ord_apl;
    }

    public OrderAppliances(int id, int count, int price_one) {
        this.id = id;
        this.count = count;
        this.price_one = price_one;
    }

    public OrderAppliances(int count, int price_one, Order order_ord_apl, Appliance appliance_ord_apl) {
        this.count = count;
        this.price_one = price_one;
        this.order_ord_apl = order_ord_apl;
        this.appliance_ord_apl = appliance_ord_apl;
    }

    public OrderAppliances() {
    }



}
