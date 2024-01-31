package com.springchat.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUser(String token);
    Boolean validateToken(String token, UserDetails userDetails);
    String generateToken(String userName);

    Boolean validateExpiration(String token);
}
