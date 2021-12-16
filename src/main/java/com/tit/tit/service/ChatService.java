package com.tit.tit.service;

import com.tit.tit.converter.DTO.ChatDTO;
import com.tit.tit.converter.DTO.create.ChatDTOCreate;
import com.tit.tit.model.Chat;
import com.tit.tit.model.ChatMessage;

import java.util.List;

public interface ChatService {

    void createChat(ChatDTOCreate chatDTOCreate);

    List<ChatDTO> findChatByUserId(Long userId);

    Chat findChatMessageByChatId(Long chatId);

    void delMessage(Long chatId, Long id);

    void addMessage(ChatMessage toEntity);

    void updateMsg(Long msgId, String message);

    void addUserFromChat(Long chatId, Long creatorId, Long userId);

    void delChat(Long chatId, Long userId);

    void delUserFromChat(Long chatId, Long creatorId, Long userId);
}
