package com.deniz.expense_ai_assistant.repository;

import com.deniz.expense_ai_assistant.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
