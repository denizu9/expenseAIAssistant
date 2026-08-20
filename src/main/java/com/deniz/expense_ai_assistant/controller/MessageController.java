package com.deniz.expense_ai_assistant.controller;

import com.deniz.expense_ai_assistant.dto.TelegramUpdateDto;
import com.deniz.expense_ai_assistant.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/receiveMessage")
    public ResponseEntity<?> receive(@RequestBody TelegramUpdateDto updateDTO) {
        if (updateDTO != null && updateDTO.getMessage() != null) {
            messageService.receiveMessage(updateDTO);
        }
        return ResponseEntity.ok().build();
    }
}
