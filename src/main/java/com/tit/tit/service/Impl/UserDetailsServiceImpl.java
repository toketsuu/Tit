package com.tit.tit.service.Impl;

import com.tit.tit.DAO.UserDAO;
import com.tit.tit.model.User;
import com.tit.tit.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserDAO userDAO;

    @Override
    public UserDetailsImpl loadUserByUsername(String login){
        User user = userDAO.findByEmail(login).orElseThrow(() -> {
            log.warn("User with login: {} not found", login);
           throw new UsernameNotFoundException("User with login " + login + " not found!");
        });
        return new UserDetailsImpl(user);
    }
}
