package com.deniz.expense_ai_assistant.controller;

import com.deniz.expense_ai_assistant.dto.MessageDTO;
import com.deniz.expense_ai_assistant.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/save")
    public ResponseEntity<Void> saveMessage(@RequestBody MessageDTO messageDTO) {
        messageService.saveMessage(messageDTO);
        return ResponseEntity.ok().build();
    }
}

