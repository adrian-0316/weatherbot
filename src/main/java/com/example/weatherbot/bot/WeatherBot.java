package com.example.weatherbot.bot;

import com.example.weatherbot.config.BotConfig;
import com.example.weatherbot.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class WeatherBot extends TelegramLongPollingBot {

    private final BotConfig config;
    private final WeatherService weatherService;

    // Явный конструктор с параметрами
    @Autowired
    public WeatherBot(BotConfig config, WeatherService weatherService) {
        this.config = config;
        this.weatherService = weatherService;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String chatId = update.getMessage().getChatId().toString();
            String message = update.getMessage().getText();

            if (message.startsWith("/weather")) {
                String[] parts = message.split(" ", 2);
                if (parts.length == 2) {
                    String city = parts[1].trim();
                    String reply = weatherService.getWeatherByCity(city);
                    sendText(chatId, reply);
                } else {
                    sendText(chatId, "Используй команду так: /weather <город>");
                }
            } else {
                sendText(chatId, "Привет! Введи /weather <город>, чтобы узнать погоду.");
            }
        }
    }

    private void sendText(String chatId, String text) {
        SendMessage message = new SendMessage(chatId, text);
        try {
            execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
