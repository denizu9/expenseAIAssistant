package com.deniz.expense_ai_assistant.util;

import com.deniz.expense_ai_assistant.dto.ParsedExpenseDTO;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
        String category = parts[1];
        String paymentPoint = parts[2];
        String productName = parts[3];
        String priceText = parts[4].replace(",", ".");
        String paymentMethod = parts.length > 5 ? parts[5] : null;
        String expenseDate = parts.length > 6 ? parts[6] : null;
        BigDecimal amount;
        try {
            amount = new BigDecimal(priceText);
        } catch (Exception e) {
            return null;
        }

        ParsedExpenseDTO dto = new ParsedExpenseDTO();
        dto.setPaymentPoint(paymentPoint);
        dto.setProductName(productName);
        dto.setAmount(amount);
        dto.setExpense(isExpense);
        dto.setExpenseDate(expenseDate != null ? parseDate(expenseDate) : null);
        dto.setPaymentMethod(paymentMethod);
        dto.setCategory(category);
        return dto;
    }

    private static LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.isBlank()) return null;
        String trimmed = dateStr.trim();

        DateTimeFormatter turkishDots = DateTimeFormatter.ofPattern("d.M.yyyy");
        try {
            return LocalDate.parse(trimmed, turkishDots);
        } catch (DateTimeParseException ignored) {
            // try ISO fallback
        }

        try {
            return LocalDate.parse(trimmed);
        } catch (DateTimeParseException ignored) {
            return null;
        }
    }
}
