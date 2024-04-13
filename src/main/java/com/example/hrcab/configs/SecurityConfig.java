package com.example.hrcab.configs;



import com.example.hrcab.models.Users;
import com.example.hrcab.repos.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//Конфигурация веб фильтра

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRep) {
        return username -> {
            Users users = userRep.findByUsername(username);
            if (users != null) return users;
            throw new UsernameNotFoundException("Пользователь: ‘" + username + "’ не найден");
        };
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {
        return http.authorizeHttpRequests(auth->auth
                         .requestMatchers("/", "/add").hasRole("HR")
                        .requestMatchers("/feedback/**").hasRole("USER")
                        .requestMatchers("/Login", "/Registration").anonymous()
                         .requestMatchers("/","/css/**","/img/**","/js/**").permitAll())
                        .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.loginPage("/Login"))
                .build();
    }
}