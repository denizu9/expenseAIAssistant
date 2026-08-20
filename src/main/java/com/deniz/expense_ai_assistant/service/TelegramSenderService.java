package com.deniz.expense_ai_assistant.service;

public interface TelegramSenderService {
    void sendMessage(Long chatId, String text);
}
