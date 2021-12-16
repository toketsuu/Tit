package com.tit.tit.converter;

import com.tit.tit.converter.DTO.UserDTO;
import com.tit.tit.converter.DTO.UserSearchDTO;
import com.tit.tit.converter.DTO.create.UserDTOCreate;
import com.tit.tit.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConverter {

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getDescription(),
                user.getPhoto()
        );
    }
    public UserSearchDTO toSearchDTO(User user) {
        return new UserSearchDTO(
                user.getId(),
                user.getName(),
                user.getDescription(),
                user.getPhoto()
        );
    }

    public User toEntity(UserDTOCreate user) {
        return new User(
                user.getEmail(),
                passwordEncoder.encode(user.getPassword()),
                user.getName(),
                user.getDescription(),
                user.getPhoto()
        );
    }
}
