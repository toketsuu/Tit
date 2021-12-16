package com.tit.tit.controllers;

import com.tit.tit.converter.DTO.DialogDTO;
import com.tit.tit.converter.DTO.create.MessageDTOCreate;
import com.tit.tit.converter.DialogConverter;
import com.tit.tit.model.Chat;
import com.tit.tit.model.Dialog;
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

@Api(tags = {"Dialog"})
@Validated
@RestController
@Controller
@RequiredArgsConstructor
public class DialogController {
    @Autowired
    DialogService service;
    @Autowired
    DialogConverter converter;

    @Operation(summary = "Отправить смс", description = "Все поля обезательны!!")
    @PostMapping("/add_message")
    public ResponseEntity<HttpStatus> addMessage(
            @RequestBody @Valid MessageDTOCreate message) {
        service.addMessage(converter.toEntity(message));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Удалить смс", description = "Все поля обезательны!!")
    @DeleteMapping("/del_message")
    public ResponseEntity<HttpStatus> delMessage(
            @RequestParam(value="messageId") Long messageId) {
        service.delMessage(messageId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(summary = "Изменить сообщение в чате")
    @ApiOperation(value = "")
    @PutMapping("/upd_msg")
    public ResponseEntity<HttpStatus> updMsg(
            @RequestParam(value="msgId") Long msgId,
            @RequestParam(value="message") String message) {
        service.updateMsg( msgId, message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Показать диалог", description = "Все поля обезательны!! \n userId - тек. пользователь ,sel - userId собеседника")
    @GetMapping("/im")
    public ResponseEntity<List<Dialog>> showDialog(
            @RequestParam(value="userId") Long userId,
            @RequestParam(value="sel") Long sel) {
        return new ResponseEntity<>(service.showDialog(userId, sel), HttpStatus.OK);
    }

    @Operation(summary = "Показать диалоги", description = "Все поля обезательны!! \n userId - тек. пользователь")
    @GetMapping("/ims")
    public ResponseEntity<List<DialogDTO>> showDialogs(
            @RequestParam(value="userId") Long userId) {
        return new ResponseEntity<>(service.showDialogs(userId), HttpStatus.OK);
    }

    @Operation(summary = "Удалить диалог", description = "Удаляется и у собеседника. sel - id собеседника. в списке диалогов(DialogDTO) это поле id")
    @DeleteMapping("/del_dlg")
    public ResponseEntity<HttpStatus> delDialog(
            @RequestParam(value="userId") Long userId,
            @RequestParam(value="sel") Long sel) {
        service.delDialog(userId, sel);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
