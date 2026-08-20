package com.deniz.expense_ai_assistant.util;

import com.deniz.expense_ai_assistant.dto.ParsedExpenseDTO;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

import static com.deniz.expense_ai_assistant.constants.MessageConstants.EXPENSE;

@UtilityClass
public class MessageUtil {

    public static ParsedExpenseDTO parse(String text) {
        if (text == null || text.isBlank()) {
            return null;
        }

        String[] parts = text.trim().split("\\s+");
        if (parts.length < 4) {
            return null;
        }

        boolean isExpense = EXPENSE.equalsIgnoreCase(parts[0]);
        String marketName = parts[1];
        String productName = parts[2];
        String priceText = parts[3].replace(",", ".");
        BigDecimal amount;
        try {
            amount = new BigDecimal(priceText);
        } catch (Exception e) {
            return null;
        }

        ParsedExpenseDTO dto = new ParsedExpenseDTO();
        dto.setMarketName(marketName);
        dto.setProductName(productName);
        dto.setAmount(amount);
        dto.setExpense(isExpense);
        return dto;
    }
}
