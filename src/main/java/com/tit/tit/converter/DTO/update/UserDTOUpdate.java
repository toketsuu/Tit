package com.tit.tit.converter.DTO.update;


import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTOUpdate {
    private String oldPassword;
    private String password;
    private String name;
    private String description;
    private String photo;
}
