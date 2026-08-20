package com.deniz.expense_ai_assistant.adapter;

import com.deniz.expense_ai_assistant.dto.MessageDTO;
import com.deniz.expense_ai_assistant.dto.TelegramUpdateDto;
import com.deniz.expense_ai_assistant.entity.Message;
import com.deniz.expense_ai_assistant.util.MessageUtil;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.Optional;

@UtilityClass
public class DataAdapter {

    public static MessageDTO prepareMessageDTO(TelegramUpdateDto updateDto) {
        if (Optional.ofNullable(updateDto).isEmpty() || Optional.ofNullable(updateDto.getMessage()).isEmpty()) {
            return null;
        }
        MessageDTO dto = new MessageDTO();
        dto.setTelegramUserId(updateDto.getMessage().getFrom() != null ? updateDto.getMessage().getFrom().getId() : null);
        dto.setChatId(updateDto.getMessage().getChat() != null ? updateDto.getMessage().getChat().getId() : null);
        dto.setMessageText(updateDto.getMessage().getText());
        dto.setAmount(MessageUtil.parse(updateDto.getMessage().getText()).getAmount());
        dto.setMarketName(MessageUtil.parse(updateDto.getMessage().getText()).getMarketName());
        dto.setProductName(MessageUtil.parse(updateDto.getMessage().getText()).getProductName());
        dto.setExpense(MessageUtil.parse(updateDto.getMessage().getText()).isExpense());
        dto.setExpenseDate(MessageUtil.parse(updateDto.getMessage().getText()).getExpenseDate());
        return dto;
    }

    public static Message prepareMessage(MessageDTO messageDTO) {
        Message message = new Message();
        message.setTelegramUserId(messageDTO.getTelegramUserId());
        message.setChatId(messageDTO.getChatId());
        message.setMessageText(messageDTO.getMessageText());
        message.setMessageReceivedTime(messageDTO.getExpenseDate() != null ? messageDTO.getExpenseDate().atStartOfDay() : LocalDateTime.now());
        message.setAmount(messageDTO.getAmount());
        message.setMarketName(messageDTO.getMarketName());
        message.setProductName(messageDTO.getProductName());
        message.setExpense(messageDTO.isExpense());
        return message;
    }
}
