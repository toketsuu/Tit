package com.tit.tit.converter.DTO.create;

import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class UserDTOCreate {
    private String email;
    private String password;
    private String name;
    private String description;
    private String photo;



}

