// WeatherBot.java
package com.example.weatherbot.bot;

import com.example.weatherbot.config.BotConfig;
import com.example.weatherbot.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class WeatherBot extends TelegramLongPollingBot {

    private final BotConfig config;
    private final WeatherService weatherService;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String chatId = update.getMessage().getChatId().toString();
            String text = update.getMessage().getText();

            if (text.startsWith("/weather")) {
                String[] parts = text.split(" ", 2);
                if (parts.length == 2) {
                    String city = parts[1].trim();
                    String response = weatherService.getWeatherByCity(city);
                    sendText(chatId, response);
                } else {
                    sendText(chatId, "Формат команды: /weather <город>");
                }
            } else {
                sendText(chatId, "Привет! Введи /weather <город>, чтобы узнать погоду.");
            }
        }
    }

    private void sendText(String chatId, String text) {
        SendMessage message = new SendMessage(chatId, text);
        try {
            execute(message); // Отправка сообщения
        } catch (Exception e) {
            e.printStackTrace(); // В боевом коде лучше логировать
        }
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public String getBotUsername() {
        return config.getName();
    }
}