package bsuir.korotkov.onlinestore.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class AccountPutDTO {
    @NotEmpty(message = "Email не может быть пустым")
    @Size(min=5, max = 100, message = "Размер email должен быть от 5 до 100")
    private String username;
    @NotEmpty(message = "Пароль не может быть пустым")
    private String oldPassword;


    private String newPassword;

    public AccountPutDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public AccountPutDTO(String username, String oldPassword, String newPassword) {
        this.username = username;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}

