package com.deniz.expense_ai_assistant.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ParsedExpenseDTO {
    private String marketName;
    private String productName;
    private BigDecimal amount;
    private boolean isExpense;
}
