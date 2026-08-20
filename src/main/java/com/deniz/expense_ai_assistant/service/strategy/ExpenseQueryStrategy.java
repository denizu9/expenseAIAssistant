package com.deniz.expense_ai_assistant.service.strategy;

public interface ExpenseQueryStrategy {

    boolean matches(String normalizedText);
    String buildResponse(Long chatId);
}
