package com.deniz.expense_ai_assistant.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    @Column(name = "message_received_time", nullable = false)
    private LocalDate messageReceivedTime;
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "is_expense", nullable = false)
    private boolean expense;
    @Column(name = "category")
    private String category;
    @Column(name = "payment_method")
    private String paymentMethod;
    @Column(name = "payment_point")
    private String paymentPoint;
}
