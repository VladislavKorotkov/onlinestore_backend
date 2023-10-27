package bsuir.korotkov.onlinestore.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import org.apache.catalina.User;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "customer_order")
public class Order {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "total")
    @NotEmpty(message="Стоимость не может быть пустой")
    private int total;

    @Column(name = "is_paid")
    @NotEmpty(message="Оплата не может быть пустой")
    private boolean is_paid;

    @Column(name = "is_delivered")
    @NotEmpty(message="Доставка не может быть пустой")
    private boolean is_delivered;

    @Column(name="date_of_sale")
    @NotEmpty(message = "Дата не может быть пустой")
    private LocalDateTime date_of_sale;

    @ManyToOne
    @JoinColumn(name="address_id", referencedColumnName = "id")
    private Address address_order;

    @ManyToOne
    @JoinColumn(name="account_id", referencedColumnName = "id")
    private Account account_order;

    @OneToMany(mappedBy = "order_ord_apl")
    private List<OrderAppliances> order_appliances;

    public Order() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setIs_paid(boolean is_paid) {
        this.is_paid = is_paid;
    }

    public void setIs_delivered(boolean is_delivered) {
        this.is_delivered = is_delivered;
    }

    public void setDate_of_sale(LocalDateTime date_of_sale) {
        this.date_of_sale = date_of_sale;
    }

    public void setAddress_order(Address address_order) {
        this.address_order = address_order;
    }

    public void setAccount_order(Account account_order) {
        this.account_order = account_order;
    }

    public void setOrder_appliances(List<OrderAppliances> order_appliances) {
        this.order_appliances = order_appliances;
    }

    public int getId() {
        return id;
    }

    public int getTotal() {
        return total;
    }

    public boolean isIs_paid() {
        return is_paid;
    }

    public boolean isIs_delivered() {
        return is_delivered;
    }

    public LocalDateTime getDate_of_sale() {
        return date_of_sale;
    }

    public Address getAddress_order() {
        return address_order;
    }

    public Account getAccount_order() {
        return account_order;
    }

    public List<OrderAppliances> getOrder_appliances() {
        return order_appliances;
    }

    public Order(int id, int total, boolean is_paid, boolean is_delivered, LocalDateTime date_of_sale) {
        this.id = id;
        this.total = total;
        this.is_paid = is_paid;
        this.is_delivered = is_delivered;
        this.date_of_sale = date_of_sale;
    }




}
