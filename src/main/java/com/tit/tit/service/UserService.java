package com.tit.tit.service;

import com.tit.tit.converter.DTO.UserDTO;
import com.tit.tit.converter.DTO.UserSearchDTO;
import com.tit.tit.converter.DTO.update.UserDTOUpdate;
import com.tit.tit.model.User;

import java.util.List;

public interface UserService {
     User findUserById(Long id);

     User toCreate(User user);

    void addFriend(Long userId, Long friendId);

    void delFriend(Long userId, Long friendId);

    User updateUser(Long id, UserDTOUpdate user);

    List<UserSearchDTO> findUsersByName(String name);

    List<UserDTO> showFriends(Long userId);
}
