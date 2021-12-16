package com.tit.tit.converter.DTO;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserDTO {
    private Long id;
    private String email;
    private String name;
    private String description;
    private String photo;
}
