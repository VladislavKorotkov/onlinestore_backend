package bsuir.korotkov.onlinestore.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "rating")
public class Rating {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "rate")
    @NotEmpty(message = "Рейтинг не может быть пустым")
    @Range(min = 1, max = 5)
    private int rate;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account_rating;

    @ManyToOne
    @JoinColumn(name = "appliance_id", referencedColumnName = "id")
    private Appliance appliance_rating;

    public void setId(int id) {
        this.id = id;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public void setAccount_rating(Account account_rating) {
        this.account_rating = account_rating;
    }

    public void setAppliance_rating(Appliance appliance_rating) {
        this.appliance_rating = appliance_rating;
    }

    public Rating(int id, int rate) {
        this.id = id;
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public int getRate() {
        return rate;
    }

    public Account getAccount_rating() {
        return account_rating;
    }

    public Appliance getAppliance_rating() {
        return appliance_rating;
    }

    public Rating() {
    }

}
