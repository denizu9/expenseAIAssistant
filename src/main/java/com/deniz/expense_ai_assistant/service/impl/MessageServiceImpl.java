package com.deniz.expense_ai_assistant.service.impl;

import com.deniz.expense_ai_assistant.adapter.DataAdapter;
import com.deniz.expense_ai_assistant.dto.MessageDTO;
import com.deniz.expense_ai_assistant.dto.TelegramUpdateDto;
import com.deniz.expense_ai_assistant.repository.MessageRepository;
import com.deniz.expense_ai_assistant.service.MessageService;
import com.deniz.expense_ai_assistant.service.TelegramSenderService;
import com.deniz.expense_ai_assistant.service.strategy.ExpenseQueryStrategy;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final TelegramSenderService telegramSenderService;
    private final List<ExpenseQueryStrategy> expenseQueryStrategies;

    @Override
    public void receiveMessage(TelegramUpdateDto updateDto) {
        try {
            String text = updateDto.getMessage().getText().trim();
            String normalized = text.toLowerCase(Locale.forLanguageTag("tr"));
            Optional<ExpenseQueryStrategy> strategy = expenseQueryStrategies.stream()
                    .filter(s -> s.matches(normalized))
                    .findFirst();
            if (strategy.isPresent()) {
                String response = strategy.get().buildResponse(updateDto.getMessage().getChat().getId());
                telegramSenderService.sendMessage(updateDto.getMessage().getChat().getId(), response);
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
}
