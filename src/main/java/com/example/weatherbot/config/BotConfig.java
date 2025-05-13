package com.example.weatherbot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotConfig {

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String token;

    @Value("${weather.api.key}")
    private String weatherApiKey;

    public String getBotName() {
        return botName;
    }

    public String getToken() {
        return token;
    }

    public String getWeatherApiKey() {
        return weatherApiKey;
    }
}
