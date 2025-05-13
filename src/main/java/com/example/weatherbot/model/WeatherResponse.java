package com.example.weatherbot.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WeatherResponse {

    private Main main;
    private List<Weather> weather;

    @Getter
    @Setter
    public static class Main {
        private double temp;
        private double feels_like;
        private int humidity;
    }

    @Getter
    @Setter
    public static class Weather {
        private String description;
    }
}