package com.deniz.expense_ai_assistant.service.impl;

import com.deniz.expense_ai_assistant.service.TelegramSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class TelegramSenderServiceImpl implements TelegramSenderService {

    @Value("${telegram.bot.token}")
    private String botToken;

    private final RestClient restClient = RestClient.create();

    @Override
    public void sendMessage(Long chatId, String text) {
        if (chatId == null || text == null || text.isBlank()) return;

        String url = "https://api.telegram.org/bot" + botToken + "/sendMessage";

        restClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of(
                        "chat_id", chatId,
                        "text", text
                ))
                .retrieve()
                .toBodilessEntity();
    }
}
