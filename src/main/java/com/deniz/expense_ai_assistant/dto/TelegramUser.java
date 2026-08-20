package com.deniz.expense_ai_assistant.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TelegramUser {
    private Long id;
    private Boolean is_bot;
    private String first_name;
    private String username;
}
