package com.tit.tit.converter;

import com.tit.tit.converter.DTO.create.MessageDTOCreate;
import com.tit.tit.model.Dialog;
import com.tit.tit.model.enums.DialogStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class DialogConverter {
    public Dialog toEntity(MessageDTOCreate message) {
        return new Dialog(
                message.getSender(),
                message.getRecipient(),
                message.getSender() < message.getRecipient() ? message.getSender() +"_"+ message.getRecipient():message.getRecipient() +"_"+ message.getSender(),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
                message.getMessage(),
                DialogStatus.SHIPPED.ordinal()
        );
    }
}