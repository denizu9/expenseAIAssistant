package com.deniz.expense_ai_assistant.service.strategy;

import com.deniz.expense_ai_assistant.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MonthlyExpenseStrategyCreditCard implements ExpenseQueryStrategy{

    private final MessageRepository messageRepository;

    @Override
    public boolean matches(String normalizedText) {
        if (Optional.ofNullable(normalizedText).isEmpty()) {
            return false;
        }
        String expectedInput = "kredi kartı bu ay toplam ne kadar harcadım";
        String input = normalizedText.trim().replaceAll("\\s+", " ");
        return input.equalsIgnoreCase(expectedInput);
    }

    @Override
    public String buildResponse(Long chatId) {
        LocalDate start = LocalDate.now().withDayOfMonth(1);
        LocalDate end = LocalDate.now().plusMonths(1).withDayOfMonth(1).minusDays(1);
        BigDecimal total = safeSum(chatId, start, end);
        return "Bu ayın toplam kredi kartı harcaması: " + total + " TL";
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
