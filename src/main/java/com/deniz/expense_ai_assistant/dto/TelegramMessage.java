package com.deniz.expense_ai_assistant.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TelegramMessage {
    private Long message_id;
    private Long date;
    private TelegramUser from;
    private TelegramChat chat;
    private String text;
}
