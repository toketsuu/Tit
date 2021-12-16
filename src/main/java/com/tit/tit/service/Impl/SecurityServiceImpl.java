package com.tit.tit.service.Impl;

import com.tit.tit.security.TokenProvider;
import com.tit.tit.security.UserDetailsImpl;
import com.tit.tit.service.SecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class SecurityServiceImpl implements SecurityService {


    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final TokenProvider provider;

    @Override
    public String authUser(String login, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
            UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(login);
            return provider.createToken(userDetails);
        } catch (AuthenticationException exception) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Login or password!");
        }
    }
}
