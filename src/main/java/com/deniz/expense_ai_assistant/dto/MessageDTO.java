package com.deniz.expense_ai_assistant.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class MessageDTO {
    private Long telegramUserId;
    private Long chatId;
    private String messageText;
    private BigDecimal amount;
    private String messageReceivedTime;
    private String paymentPoint;
    private String productName;
    private boolean isExpense;
    private LocalDate expenseDate;
    private String paymentMethod;
    private String category;
}
