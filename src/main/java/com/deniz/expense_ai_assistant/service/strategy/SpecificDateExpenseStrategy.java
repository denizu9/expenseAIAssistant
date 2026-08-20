package com.deniz.expense_ai_assistant.service.strategy;

import com.deniz.expense_ai_assistant.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class SpecificDateExpenseStrategy implements ExpenseQueryStrategy {

    private final MessageRepository messageRepository;
    private static final Pattern DATE_PATTERN = Pattern.compile("\\b(\\d{1,2}[\\.\\-/]\\d{1,2}[\\.\\-/]\\d{4})\\b");
    private static final ThreadLocal<LocalDate> MATCHED_DATE = new ThreadLocal<>();
    private static final DateTimeFormatter PARSER = DateTimeFormatter.ofPattern("d.M.uuuu");
    private static final DateTimeFormatter OUTPUT_FMT = DateTimeFormatter.ofPattern("dd.MM.uuuu");

    @Override
    public boolean matches(String normalizedText) {
        if (Optional.ofNullable(normalizedText).isEmpty()) {
            return false;
        }
        Matcher m = DATE_PATTERN.matcher(normalizedText);
        if (!m.find()) {
            return false;
        }

        String raw = m.group(1);
        String normalized = raw.replace('-', '.').replace('/', '.');

        try {
            LocalDate date = LocalDate.parse(normalized, PARSER);
            MATCHED_DATE.set(date);
            return true;
        } catch (DateTimeParseException e) {
            MATCHED_DATE.remove();
            return false;
        }
    }

    @Override
    public String buildResponse(Long chatId) {
        LocalDate date = MATCHED_DATE.get();
        MATCHED_DATE.remove();
        if (date == null) {
            return "Tarih bulunamadı veya geçersiz.";
        }

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay().minusNanos(1);
        BigDecimal total = safeSum(chatId, start, end);

        return date.format(OUTPUT_FMT) + " tarihinde toplam harcama: " + total + " TL";
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
