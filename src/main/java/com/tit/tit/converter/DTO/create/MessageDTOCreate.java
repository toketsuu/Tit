package com.tit.tit.converter.DTO.create;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTOCreate {
    private Long sender;
    private Long recipient;
    private String message;
}