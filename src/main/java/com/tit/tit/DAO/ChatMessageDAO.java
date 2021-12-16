package com.tit.tit.DAO;

import com.tit.tit.model.Chat;
import com.tit.tit.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageDAO extends JpaRepository<ChatMessage, Long> {

}
