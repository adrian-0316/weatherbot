// WeatherService.java
package com.example.weatherbot.service;

import com.example.weatherbot.config.BotConfig;
import com.example.weatherbot.model.WeatherResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final BotConfig config;
    private final RestTemplate restTemplate = new RestTemplate();

    public String getWeatherByCity(String city) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city +
                "&appid=" + config.getWeatherApiKey() +
                "&units=metric&lang=ru";

        try {
            WeatherResponse response = restTemplate.getForObject(url, WeatherResponse.class);
            if (response == null || response.getMain() == null) {
                return "Не удалось получить данные. Проверь название города.";
            }

            return String.format(
                    "Погода в %s:\n🌡 Температура: %.1f°C\n🤔 Ощущается как: %.1f°C\n💧 Влажность: %d%%\n📋 %s",
                    city,
                    response.getMain().getTemp(),
                    response.getMain().getFeelsLike(),
                    response.getMain().getHumidity(),
                    response.getWeather().get(0).getDescription()
            );
        } catch (Exception e) {
            return "Ошибка при получении погоды: " + e.getMessage();
        }
    }
}