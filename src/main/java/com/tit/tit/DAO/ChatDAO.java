package com.tit.tit.DAO;

import com.tit.tit.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatDAO extends JpaRepository<Chat, Long> {
    @Query(value = "SELECT * FROM chat as c join chat_user as u on c.chat_id = u.chat_id where u.user_id=:userId", nativeQuery = true)
    List<Chat> findChats(Long userId);
}
