package com.springchat.service.impl;

import com.springchat.models.request.UserAuthRequest;
import com.springchat.persistence.repository.UserRepository;
import com.springchat.service.AuthenticationService;
import com.springchat.service.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    public AuthenticationServiceImpl(UserRepository userRepository,JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public String authenticate(UserAuthRequest userAuthRequest, Authentication authentication) {
        if(authentication.isAuthenticated()) {
            return jwtService.generateToken(userAuthRequest.getUsername());
        }
        return "null";
    }

    @Override
    public String logout(String token) {
        return null;
    }
}
