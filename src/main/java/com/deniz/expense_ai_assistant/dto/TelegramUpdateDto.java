package com.deniz.expense_ai_assistant.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TelegramUpdateDto {
    private Long update_id;
    private TelegramMessage message;
}
