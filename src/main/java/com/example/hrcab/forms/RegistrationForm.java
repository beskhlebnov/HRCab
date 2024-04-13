package com.example.hrcab.forms;


import com.example.hrcab.models.Users;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;


//Форма регистрации
@Data
public class RegistrationForm {
    private String username;
    private String about;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String password;
    public Users toUser(PasswordEncoder passwordEncoder) {
        return new Users(username, about, firstName, lastName, patronymic, passwordEncoder.encode(password));
    }
}
