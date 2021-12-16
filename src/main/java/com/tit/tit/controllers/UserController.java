package com.tit.tit.controllers;

import com.tit.tit.DAO.UserDAO;
import com.tit.tit.converter.DTO.AuthenticationResponseDTO;
import com.tit.tit.converter.DTO.LoginDTO;
import com.tit.tit.converter.DTO.UserDTO;
import com.tit.tit.converter.DTO.UserSearchDTO;
import com.tit.tit.converter.DTO.create.UserDTOCreate;
import com.tit.tit.converter.DTO.update.UserDTOUpdate;
import com.tit.tit.converter.UserConverter;
import com.tit.tit.service.SecurityService;
import com.tit.tit.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {"User"})
@Validated
@RestController
@Controller
@RequiredArgsConstructor
public class UserController {
    @Autowired
    UserConverter converter;
    @Autowired
    UserService service;
    @Autowired
    UserDAO userDAO;
    @Autowired
    private final SecurityService securityService;

    @Operation(summary = "Регистрация")
    @PostMapping("/reg")
    public ResponseEntity<AuthenticationResponseDTO> registration(
            @RequestBody @Valid UserDTOCreate user) {
        UserDTO userDTO = converter.toDTO(service.toCreate(converter.toEntity(user)));
        return new ResponseEntity<>(new AuthenticationResponseDTO(securityService.authUser(userDTO.getEmail(), user.getPassword()), userDTO.getId()), HttpStatus.CREATED);
    }

    @Operation(summary = "Аунтификация")
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> authentication(
            @RequestBody @Valid LoginDTO user) {
        return new ResponseEntity<>(new AuthenticationResponseDTO(securityService.authUser(user.getLogin(), user.getPassword()),userDAO.findByEmail(user.getLogin()).orElseThrow().getId()), HttpStatus.OK);
    }

    @Operation(summary = "Вывод user")
    @GetMapping("/get_user")
    public ResponseEntity<UserDTO> getUser(@RequestParam(value="id") Long id) {
        return new ResponseEntity<>(
                converter.toDTO(service.findUserById(id)),
                HttpStatus.OK);
    }

    @Operation(summary = "Поиск пользователя для добавления в друзья по имени")
    @GetMapping("/search_user")
    public ResponseEntity<List<UserSearchDTO>> searchUser(@RequestParam(value="name") String name) {
        return new ResponseEntity<>(
                service.findUsersByName(name),
                HttpStatus.OK);
    }

    @Operation(summary = "Изменить данные о пользователе")
    @ApiOperation(value = "")
    @PutMapping("/update_user")
    public ResponseEntity<UserDTO> updateUser(
            @RequestParam(value="userId") Long userId,
            @RequestBody @Valid UserDTOUpdate user) {
        return new ResponseEntity<>(
                converter.toDTO(service.updateUser(userId, user)),
                HttpStatus.OK);
    }

    @Operation(summary = "Добавить друга")
    @GetMapping("/add_friend")
    public ResponseEntity<HttpStatus> addFriend(
            @RequestParam(value="userId") Long userId,
            @RequestParam(value="friendId") Long friendId) {
        service.addFriend(userId, friendId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Показать друзей")
    @GetMapping("/show_friends")
    public ResponseEntity<List<UserDTO>> showFriends(
            @RequestParam(value="userId") Long userId) {
        return new ResponseEntity<>(service.showFriends(userId) , HttpStatus.OK);
    }

    @Operation(summary = "Удалить друга")
    @DeleteMapping("/del_friend")
    public ResponseEntity<HttpStatus> delFriend(
            @RequestParam(value="userId") Long userId,
            @RequestParam(value="friendId") Long friendId) {
        service.delFriend(userId, friendId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
