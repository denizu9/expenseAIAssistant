package com.deniz.expense_ai_assistant.service.strategy;

import com.deniz.expense_ai_assistant.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class YesterdayExpenseStrategy implements ExpenseQueryStrategy {

    private final MessageRepository messageRepository;

    @Override
    public boolean matches(String normalizedText) {
        if (Optional.ofNullable(normalizedText).isEmpty()) {
            return false;
        }
        String expectedInput = "dün toplam ne kadar harcadım";
        String input = normalizedText.trim().replaceAll("\\s+", " ");
        return input.equals(expectedInput);
    }

    @Override
    public String buildResponse(Long chatId) {
        LocalDateTime start = LocalDate.now().minusDays(1).atStartOfDay();
        LocalDateTime end = LocalDate.now().atStartOfDay().minusNanos(1);
        BigDecimal total = safeSum(chatId, start, end);
        return "Dünün toplam harcaması: " + total + " TL";
    }

    private BigDecimal safeSum(Long chatId, LocalDateTime start, LocalDateTime end) {
        try {
            BigDecimal result = messageRepository.sumAmountByChatIdAndIsExpenseTrueAndMessageReceivedTimeBetween(chatId, start, end);
            return result == null ? BigDecimal.ZERO : result;
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

}
