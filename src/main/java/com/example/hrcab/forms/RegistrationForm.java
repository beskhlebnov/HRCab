package com.example.hrcab.forms;


import com.example.hrcab.models.Users;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegistrationForm {
    private String username;
    private String nickname;
    private String password;
    public Users toUser(PasswordEncoder passwordEncoder) {
        return new Users(username, nickname, passwordEncoder.encode(password));
    }
}
