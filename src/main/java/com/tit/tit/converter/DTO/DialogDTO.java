package com.tit.tit.converter.DTO;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DialogDTO {

    private Long id;
    private String name;
    private String message;
    private String time;
    private String photo;
    private int status;
}