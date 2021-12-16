package com.tit.tit.converter;

import com.tit.tit.DAO.ChatDAO;
import com.tit.tit.converter.DTO.ChatDTO;
import com.tit.tit.converter.DTO.create.ChatMessageDTOCreate;
import com.tit.tit.converter.DTO.create.MessageDTOCreate;
import com.tit.tit.model.Chat;
import com.tit.tit.model.ChatMessage;
import com.tit.tit.model.Dialog;
import com.tit.tit.model.enums.DialogStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class ChatConverter {

    @Autowired
    ChatDAO chatDAO;

    public ChatDTO toDTO(Chat chat)
    {
        return new ChatDTO(
                chat.getName(),
                chat.getCreateTime(),
                chat.getPhoto()
        );
    }

    public ChatMessage toEntity(ChatMessageDTOCreate message) {
        return new ChatMessage(
                message.getSender(),
                message.getMessage(),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
                chatDAO.findById(message.getChatId()).orElseThrow()
        );
    }
}