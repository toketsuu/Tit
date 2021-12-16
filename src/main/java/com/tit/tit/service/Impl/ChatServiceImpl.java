package com.tit.tit.service.Impl;

import com.tit.tit.DAO.ChatDAO;
import com.tit.tit.DAO.ChatMessageDAO;
import com.tit.tit.DAO.UserDAO;
import com.tit.tit.converter.ChatConverter;
import com.tit.tit.converter.DTO.ChatDTO;
import com.tit.tit.converter.DTO.create.ChatDTOCreate;
import com.tit.tit.model.Chat;
import com.tit.tit.model.ChatMessage;
import com.tit.tit.model.User;
import com.tit.tit.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    @Autowired
    ChatDAO chatDAO;
    @Autowired
    ChatMessageDAO chatMessageDAO;
    @Autowired
    ChatConverter converter;
    @Autowired
    UserDAO userDAO;

    @Override
    public void createChat(ChatDTOCreate chatDTOCreate) {
        List<User> users = new ArrayList<>();
        for (Long id : chatDTOCreate.getMembers())
            users.add(userDAO.findById(id).orElseThrow());
        chatDAO.save(new Chat(
                chatDTOCreate.getName(),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
                chatDTOCreate.getPhoto(),
                chatDTOCreate.getCreator(),
                users)
        );
    }

    @Override
    public List<ChatDTO> findChatByUserId(Long userId) {
        List<ChatDTO> dtoList = new ArrayList<>();
        for (Chat chat : chatDAO.findChats(userId))
            dtoList.add(converter.toDTO(chat));
        return dtoList;
    }

    @Override
    public Chat findChatMessageByChatId(Long chatId) {
        return chatDAO.findById(chatId).orElseThrow();
    }

    @Override
    public void delMessage(Long chatId, Long id) {
        chatMessageDAO.findById(id);
        chatDAO.findById(chatId).ifPresentOrElse(
                chat -> chat.getMessages().removeIf(message -> message.getId().equals(id)),
                () -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "chat is not found, id: " + chatId);
                }
        );
    }

    @Override
    public void addMessage(ChatMessage message) {
        chatDAO.findById(message.getChat().getId()).ifPresentOrElse(
                chat -> {
                    chat.getMessages().add(message);
                    chatDAO.saveAndFlush(chat);
                },
                () -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "chat is not found, id: " + message.getChat().getId());
                }
        );
    }

    @Override
    public void updateMsg(Long msgId, String message) {
        chatMessageDAO.findById(msgId).ifPresentOrElse(
                msg -> {
                    if (msg.getMessage().length() > 8 && msg.getMessage().endsWith(" (Изменено)"))
                        msg.setMessage(msg.getMessage().substring(0, msg.getMessage().length() - 11));
                    msg.setMessage(message + " (Изменено)");
                },
                () -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "message is not found, id: " + msgId);
                }
        );
    }

    @Override
    public void addUserFromChat(Long chatId, Long creatorId, Long userId) {
        chatDAO.findById(chatId).ifPresent(
                chat -> {
                    if (chat.getCreator().equals(creatorId)) {
                        chat.getUser().add(userDAO.findById(userId).orElseThrow());
                        chatDAO.saveAndFlush(chat);
                    }
                });
    }

    @Override
    public void delChat(Long chatId, Long userId) {
        chatDAO.findById(chatId).ifPresent(
                chat -> {
                    if (chat.getCreator().equals(userId))
                        chatDAO.delete(chat);
                });
    }

    @Override
    public void delUserFromChat(Long chatId, Long creatorId, Long userId) {
        chatDAO.findById(chatId).ifPresent(
                chat -> {
                    if (chat.getCreator().equals(creatorId)) {
                        chat.getUser().remove(userDAO.findById(userId).orElseThrow());
                        chatDAO.saveAndFlush(chat);
                    }
                });
    }
}
