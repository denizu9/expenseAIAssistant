package com.deniz.expense_ai_assistant.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDTO {
    private Long telegramUserId;
    private Long chatId;
    private String messageText;
    private String rawUpdateJson;
    private String messageReceivedTime;
}
