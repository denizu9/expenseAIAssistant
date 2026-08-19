package com.deniz.expense_ai_assistant.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "message")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "telegram_user_id", nullable = false)
    private Long telegramUserId;
    @Column(name = "chat_id", nullable = false)
    private Long chatId;
    @Column(name = "message_text", nullable = false)
    private String messageText;
    @Column(name = "raw_update_json", nullable = false)
    private String rawUpdateJson;
    @Column(name = "message_received_time", nullable = false)
    private LocalDateTime messageReceivedTime;
}
