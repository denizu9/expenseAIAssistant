package com.deniz.expense_ai_assistant.service.impl;

import com.deniz.expense_ai_assistant.dto.MessageDTO;
import com.deniz.expense_ai_assistant.entity.Message;
import com.deniz.expense_ai_assistant.repository.MessageRepository;
import com.deniz.expense_ai_assistant.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void saveMessage(MessageDTO messageDTO) {
        if (Optional.ofNullable(messageDTO).isPresent() && Optional.ofNullable(messageDTO.getMessageText()).isPresent()) {
            messageRepository.save(prepareMessage(messageDTO));
        }
    }

    private String toJson(MessageDTO messageDTO) {
        try {
            return objectMapper.writeValueAsString(messageDTO);
        } catch (Exception e) {
            return "{}";
        }
    }

    private Message prepareMessage(MessageDTO messageDTO) {
        Message message = new Message();
        message.setTelegramUserId(messageDTO.getTelegramUserId());
        message.setChatId(messageDTO.getChatId());
        message.setMessageText(messageDTO.getMessageText());
        message.setRawUpdateJson(toJson(messageDTO));
        message.setMessageReceivedTime(LocalDateTime.now());
        return message;
    }
}
