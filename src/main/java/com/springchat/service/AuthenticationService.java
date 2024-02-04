package com.springchat.service;

import com.springchat.models.request.UserAuthRequest;
import com.springchat.models.response.TokenResponse;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {

    TokenResponse authenticate(UserAuthRequest userAuthRequest, Authentication authentication);

    String logout(String token);
}
