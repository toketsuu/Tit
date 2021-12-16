package com.tit.tit.converter.DTO;


import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

    @NotEmpty(message = "login should not be empty")
    @Size(max = 256, message = "login must be less than 256 characters")
    private String login;

    @NotEmpty(message = "password should not be empty")
    @Size(max = 256, message = "password must be less than 256 characters")
    private String  password;

}