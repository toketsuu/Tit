package com.tit.tit.converter.DTO;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserSearchDTO {
    private Long id;
    private String name;
    private String description;
    private String photo;
}
