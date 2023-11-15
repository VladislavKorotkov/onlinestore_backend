package bsuir.korotkov.onlinestore.dto;

import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

public class RatingDTO {
    @NotEmpty(message = "Рейтинг не может быть пустым")
    @Range(min = 1, max = 5)
    private int rate;

    @NotEmpty(message = "ID товара не может быть пустым")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RatingDTO(int rate, int id) {
        this.rate = rate;
        this.id = id;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public RatingDTO() {
    }

    public RatingDTO(int rate) {
        this.rate = rate;
    }
}
