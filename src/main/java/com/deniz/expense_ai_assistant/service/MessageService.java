package com.deniz.expense_ai_assistant.service;

import com.deniz.expense_ai_assistant.dto.TelegramUpdateDto;

public interface MessageService {

    void receiveMessage(TelegramUpdateDto updateDto);
}
