package com.tit.tit.controllers;

import com.tit.tit.converter.ChatConverter;
import com.tit.tit.converter.DTO.AuthenticationResponseDTO;
import com.tit.tit.converter.DTO.ChatDTO;
import com.tit.tit.converter.DTO.LoginDTO;
import com.tit.tit.converter.DTO.UserDTO;
import com.tit.tit.converter.DTO.create.ChatDTOCreate;
import com.tit.tit.converter.DTO.create.ChatMessageDTOCreate;
import com.tit.tit.converter.DTO.create.MessageDTOCreate;
import com.tit.tit.converter.DTO.update.UserDTOUpdate;
import com.tit.tit.converter.UserConverter;
import com.tit.tit.model.Chat;
import com.tit.tit.service.ChatService;
import com.tit.tit.service.DialogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {"Chat"})
@Validated
@RestController
@Controller
@RequiredArgsConstructor
public class ChatController {
    @Autowired
    ChatService service;
    @Autowired
    ChatConverter converter;

    @Operation(summary = "Создать чат", description = "в members указать id друзей, которых хотите добавить в чат. ОБЯЗАТЕЛЬНО УКАЗАТЬ И СВОЙ id текущего пользователя")
    @PostMapping("/create_chat")
    public ResponseEntity<HttpStatus> createChat(
            @RequestBody @Valid ChatDTOCreate chatDTOCreate) {
        service.createChat(chatDTOCreate);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Вывод чатов")
    @GetMapping("/get_chats")
    public ResponseEntity<List<ChatDTO>> getChats(@RequestParam(value="userId") Long userId) {
        return new ResponseEntity<>(
                service.findChatByUserId(userId),
                HttpStatus.OK);
    }

    @Operation(summary = "Отправить смс в чат", description = "Все поля обезательны!!")
    @PostMapping("/add_chat_msg")
    public ResponseEntity<HttpStatus> addChatMessage(
            @RequestBody @Valid ChatMessageDTOCreate message) {
        service.addMessage(converter.toEntity(message));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Вывод сообщений в чате")
    @GetMapping("/get_messages")
    public ResponseEntity<Chat> getMessages(@RequestParam(value="chatId") Long chatId) {
        return new ResponseEntity<>(
                service.findChatMessageByChatId(chatId),
                HttpStatus.OK);
    }

    @Operation(summary = "Изменить сообщение в чате")
    @ApiOperation(value = "")
    @PutMapping("/update_msg")
    public ResponseEntity<HttpStatus> updateMsg(
            @RequestParam(value="msgId") Long msgId,
            @RequestParam(value="message") String message) {
        service.updateMsg( msgId, message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "добавить пользователя в чат", description = "добавлять можно только из списка друзей")
    @ApiOperation(value = "")
    @PutMapping("/add_user_from_chat")
    public ResponseEntity<HttpStatus> addUserFromChat(
            @RequestParam(value="chatId") Long chatId,
            @RequestParam(value="creatorId") Long creatorId,
            @RequestParam(value="userId") Long userId) {
        service.addUserFromChat( chatId, creatorId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Удалить сообщение по id")
    @DeleteMapping("/del_chat_message")
    public ResponseEntity<HttpStatus> delMessage(
            @RequestParam(value="chatId") Long chatId,
            @RequestParam(value="id") Long id) {
        service.delMessage(chatId, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Удалить чат по id", description = "Удалять может только создатель чата")
    @DeleteMapping("/del_chat")
    public ResponseEntity<HttpStatus> delChat(
            @RequestParam(value="chatId") Long chatId,
            @RequestParam(value="userId") Long userId) {
        service.delChat(chatId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Удалить пользователя из чата", description = "Удалять может только создатель чата")
    @DeleteMapping("/del_user_from_chat")
    public ResponseEntity<HttpStatus> delUserFromChat(
            @RequestParam(value="chatId") Long chatId,
            @RequestParam(value="creatorId") Long creatorId,
            @RequestParam(value="userId") Long userId) {
        service.delUserFromChat(chatId, creatorId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
