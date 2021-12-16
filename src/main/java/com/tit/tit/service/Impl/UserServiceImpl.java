package com.tit.tit.service.Impl;

import com.tit.tit.DAO.UserDAO;
import com.tit.tit.controllers.UserController;
import com.tit.tit.converter.DTO.UserDTO;
import com.tit.tit.converter.DTO.UserSearchDTO;
import com.tit.tit.converter.DTO.update.UserDTOUpdate;
import com.tit.tit.converter.UserConverter;
import com.tit.tit.model.User;
import com.tit.tit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    UserDAO userDAO;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserConverter converter;

    @Override
    public User findUserById(Long id) {
        return userDAO.findById(id).orElseThrow();
    }

    @Override
    public User toCreate(User user) {
        userDAO.findByEmail(user.getEmail()).ifPresentOrElse(
                u -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user already exists");
                },
                () -> userDAO.save(user)
        );
        return userDAO.findByEmail(user.getEmail()).orElseThrow();
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
        userDAO.findById(userId).ifPresentOrElse(
                user -> userDAO.findById(friendId).ifPresentOrElse(
                        friend -> {
                            List<User> friends = user.getFriends();
                            friends.add(friend);
                            user.setFriends(friends);
                        },
                        () -> {
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "friend is not found, id: " + friendId);
                        }
                ),
                () -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User is not found, id: " + userId);
                }
        );
    }

    @Override
    public void delFriend(Long userId, Long friendId) {
        userDAO.findById(userId).ifPresentOrElse(
                user -> userDAO.findById(friendId).ifPresentOrElse(
                        friend -> {
                            List<User> friends = user.getFriends();
                            friends.remove(friend);
                            user.setFriends(friends);
                        },
                        () -> {
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "friend is not found, id: " + friendId);
                        }
                ),
                () -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User is not found, id: " + userId);
                }
        );
    }

    @Override
    public User updateUser(Long id, UserDTOUpdate user) {
        userDAO.findById(id).ifPresentOrElse(
                u -> {
                    if (user.getOldPassword() != null && user.getPassword() != null) {
                        if (u.getPassword().equals(passwordEncoder.encode(user.getOldPassword())))
                            u.setPassword(passwordEncoder.encode(user.getPassword()));
                        else
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "passwords don't match");
                    }
                    if (user.getName() != null)
                        u.setName(user.getName());
                    if (user.getDescription() != null)
                        u.setDescription(user.getDescription());
                    if (user.getPhoto() != null)
                        u.setPhoto(user.getPhoto());
                },
                () -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User is not found, id: " + id);
                }
        );
        return userDAO.findById(id).orElseThrow();
    }

    @Override
    public List<UserSearchDTO> findUsersByName(String name) {
        List<UserSearchDTO> searchDTOList = new ArrayList<>();
        for(User user : userDAO.findUserByName(name))
            searchDTOList.add(converter.toSearchDTO(user));
        return searchDTOList;
    }

    @Override
    public List<UserDTO> showFriends(Long userId) {
        List<UserDTO> dtoList = new ArrayList<>();
        userDAO.findById(userId).ifPresentOrElse(
                user -> {
                    for(User u : user.getFriends())
                        dtoList.add(converter.toDTO(u));
                },
                () -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User is not found, id: " + userId);
                }
        );
        return dtoList;
    }
}
