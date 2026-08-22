package com.deniz.expense_ai_assistant.service.strategy;

import com.deniz.expense_ai_assistant.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TodayExpenseStrategyCreditCard implements ExpenseQueryStrategy{

    private MessageRepository messageRepository;

    @Override
    public boolean matches(String normalizedText) {
        if (Optional.ofNullable(normalizedText).isEmpty()) {
            return false;
        }
        String expectedInput = "kredi kartı bugün toplam ne kadar harcadım";
        String input = normalizedText.trim().replaceAll("\\s+", " ");
        return input.equalsIgnoreCase(expectedInput);
    }

    @Override
    public String buildResponse(Long chatId) {
        LocalDate today = LocalDate.now();
        BigDecimal total = safeSum(chatId, today, today);
        return "Bugünün toplam kredi kartı harcaması: " + total + " TL";
    }

    private BigDecimal safeSum(Long chatId, LocalDate start, LocalDate end) {
        try {
            BigDecimal result = messageRepository.sumAmountByChatIdAndIsExpenseAndPaymentMethodTrueAndMessageReceivedTimeBetween(chatId, start, end);
            return result == null ? BigDecimal.ZERO : result;
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }
}
