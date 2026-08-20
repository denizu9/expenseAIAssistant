package com.deniz.expense_ai_assistant.service.impl;

import com.deniz.expense_ai_assistant.adapter.DataAdapter;
import com.deniz.expense_ai_assistant.dto.MessageDTO;
import com.deniz.expense_ai_assistant.dto.TelegramUpdateDto;
import com.deniz.expense_ai_assistant.repository.MessageRepository;
import com.deniz.expense_ai_assistant.service.MessageService;
import com.deniz.expense_ai_assistant.service.TelegramSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final TelegramSenderService telegramSenderService;

    @Override
    public void saveMessage(TelegramUpdateDto updateDto) {
        try {
            String text = updateDto.getMessage().getText().trim();
            String normalized = text.toLowerCase(Locale.forLanguageTag("tr"));
            if (isTodayExpenseQuery(normalized)) {
                LocalDateTime start = LocalDate.now().atStartOfDay();
                LocalDateTime end = LocalDate.now().plusDays(1).atStartOfDay().minusNanos(1);
                BigDecimal total = getTodayExpenses(updateDto.getMessage().getChat().getId(),start, end);
                String reply = "Bugünün toplam harcaması: " + total + " TL";
                telegramSenderService.sendMessage(updateDto.getMessage().getChat().getId(), reply);
                return;
            } else if (isMonthExpenseQuery(normalized)) {
                LocalDateTime start = LocalDate.now().withDayOfMonth(1).atStartOfDay();
                LocalDateTime end = LocalDate.now().plusMonths(1).withDayOfMonth(1).atStartOfDay().minusNanos(1);
                BigDecimal total = getTodayExpenses(updateDto.getMessage().getChat().getId(),start, end);
                String reply = "Bu ayın toplam harcaması: " + total + " TL";
                telegramSenderService.sendMessage(updateDto.getMessage().getChat().getId(), reply);
                return;
            } else if (isYesterdayExpenseQuery(normalized)) {
                LocalDateTime start = LocalDate.now().minusDays(1).atStartOfDay();
                LocalDateTime end = LocalDate.now().atStartOfDay().minusNanos(1);
                BigDecimal total = getTodayExpenses(updateDto.getMessage().getChat().getId(),start, end);
                String reply = "Dünün toplam harcaması: " + total + " TL";
                telegramSenderService.sendMessage(updateDto.getMessage().getChat().getId(), reply);
                return;
            }
            MessageDTO messageDTO = DataAdapter.prepareMessageDTO(updateDto);
            if (Optional.ofNullable(messageDTO).isPresent() && Optional.ofNullable(messageDTO.getMessageText()).isPresent()) {
                messageRepository.save(DataAdapter.prepareMessage(messageDTO));
                telegramSenderService.sendMessage(messageDTO.getChatId(), "Kaydettim ✅");
            }
        } catch (Exception e) {
            try {
                telegramSenderService.sendMessage(updateDto.getUpdate_id(), e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
            } catch (Exception ignored) {
            }
        }
    }

    private BigDecimal getTodayExpenses(Long chatId, LocalDateTime startOfDay, LocalDateTime endOfDay) {
        try {
            return messageRepository.sumAmountByChatIdAndIsExpenseTrueAndMessageReceivedTimeBetween(chatId, startOfDay, endOfDay);
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    private boolean isTodayExpenseQuery(String normalizedText) {
        if (normalizedText == null || normalizedText.isBlank()) return false;
        String canonical = "bugün toplam ne kadar harcadım";
        String cleaned = normalizedText.trim().replaceAll("\\s+", " ");
        return cleaned.equals(canonical);
    }

    private boolean isMonthExpenseQuery(String normalizedText) {
        if (normalizedText == null || normalizedText.isBlank()) return false;
        String canonical = "bu ay toplam ne kadar harcadım";
        String cleaned = normalizedText.trim().replaceAll("\\s+", " ");
        return cleaned.equals(canonical);
    }

    private boolean isYesterdayExpenseQuery(String normalizedText) {
        if (normalizedText == null || normalizedText.isBlank()) return false;
        String canonical = "dün toplam ne kadar harcadım";
        String cleaned = normalizedText.trim().replaceAll("\\s+", " ");
        return cleaned.equals(canonical);
    }
}
