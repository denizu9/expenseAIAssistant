package com.deniz.expense_ai_assistant.repository;

import com.deniz.expense_ai_assistant.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    boolean existsByChatIdAndMessageTextAndAmountAndMessageReceivedTimeBetween(
            Long chatId,
            String messageText,
            BigDecimal amount,
            LocalDate start,
            LocalDate end
    );

    @Query("SELECT COALESCE(SUM(m.amount), 0) FROM Message m " +
            "WHERE (:chatId IS NULL OR m.chatId = :chatId) " +
            "AND m.messageReceivedTime BETWEEN :start AND :end")
    BigDecimal sumAmountByChatIdAndIsExpenseTrueAndMessageReceivedTimeBetween(
            @Param("chatId") Long chatId,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

    @Query("SELECT COALESCE(SUM(m.amount), 0) FROM Message m " +
            "WHERE (:chatId IS NULL OR m.chatId = :chatId) " +
            "AND m.paymentMethod = 'Kredi_Kartı' " +
            "AND m.messageReceivedTime BETWEEN :start AND :end")
    BigDecimal sumAmountByChatIdAndIsExpenseAndPaymentMethodTrueAndMessageReceivedTimeBetween(
            @Param("chatId") Long chatId,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );
}
