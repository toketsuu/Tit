package com.tit.tit.converter.DTO.create;

import com.tit.tit.model.AbstractIdentity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor

public class ChatMessageDTOCreate extends AbstractIdentity {

    private Long sender;
    private String message;
    private Long chatId;
}
