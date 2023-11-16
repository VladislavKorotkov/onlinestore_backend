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
import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "rating")
public class Rating {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "rate")
    @NotNull(message = "Рейтинг не может быть пустым")
    @Range(min = 1, max = 5)
    private int rate;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account accountRating;

    @ManyToOne
    @JoinColumn(name = "appliance_id", referencedColumnName = "id")
    private Appliance applianceRating;

    public void setId(int id) {
        this.id = id;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public void setAccountRating(Account account_rating) {
        this.accountRating = account_rating;
    }

    public void setApplianceRating(Appliance appliance_rating) {
        this.applianceRating = appliance_rating;
    }

    public Rating(int id, int rate) {
        this.id = id;
        this.rate = rate;
    }

    public Rating(int rate) {
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public int getRate() {
        return rate;
    }

    public Account getAccountRating() {
        return accountRating;
    }

    public Appliance getApplianceRating() {
        return applianceRating;
    }

    public Rating() {
    }

    public Rating(int rate, Account accountRating, Appliance applianceRating) {
        this.rate = rate;
        this.accountRating = accountRating;
        this.applianceRating = applianceRating;
    }
}
