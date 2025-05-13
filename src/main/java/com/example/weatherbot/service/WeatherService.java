package com.example.weatherbot.service;

import com.example.weatherbot.config.BotConfig;
import com.example.weatherbot.model.WeatherResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final BotConfig config;
    private final RestTemplate restTemplate;

    public String getWeatherByCity(String city) {
        String url = UriComponentsBuilder.fromHttpUrl("https://api.openweathermap.org/data/2.5/weather")
                .queryParam("q", city)
                .queryParam("appid", config.getWeatherApiKey())
                .queryParam("units", "metric")
                .queryParam("lang", "ru")
                .toUriString();

        WeatherResponse response = restTemplate.getForObject(url, WeatherResponse.class);

        if (response == null || response.getMain() == null) {
            return "Не удалось получить данные о погоде. Проверьте название города.";
        }

        return String.format("Погода в городе %s:\nТемпература: %.1f°C\nОщущается как: %.1f°C\nВлажность: %d%%\n%s",
                city,
                response.getMain().getTemp(),
                response.getMain().getFeelsLike(),
                response.getMain().getHumidity(),
                response.getWeather().get(0).getDescription());
    }
}