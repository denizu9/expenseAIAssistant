package com.deniz.expense_ai_assistant.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ParsedExpenseDTO {
    private String paymentPoint;
    private String productName;
    private BigDecimal amount;
    private boolean isExpense;
    private LocalDate expenseDate;
    private String paymentMethod;
    private String category;
}
