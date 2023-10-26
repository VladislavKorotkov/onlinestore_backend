package bsuir.korotkov.onlinestore.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class AccountDTO {
    @NotEmpty(message = "Email не может быть пустым")
    @Size(min=5, max = 100, message = "Размер email должен быть от 5 до 100")
    private String username;

    @NotEmpty(message = "Пароль не может быть пустым")
    private String password;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public AccountDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AccountDTO() {
    }
}
