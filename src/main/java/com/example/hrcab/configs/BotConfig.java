package com.example.hrcab.configs;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

//Конфигурация телеграм бота

@Configuration
@Data
@PropertySource("application.yml")
public class BotConfig {

    @Value("${bot.name}")
    String botName;

    @Value("${bot.key}")
    String token;

}
