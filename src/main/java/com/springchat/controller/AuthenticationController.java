package com.springchat.controller;

import com.springchat.models.request.TokenRequest;
import com.springchat.models.request.UserAuthRequest;
import com.springchat.models.response.TokenBooleanResponse;
import com.springchat.models.response.TokenResponse;
import com.springchat.service.AuthenticationService;
import com.springchat.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticateService;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public AuthenticationController(AuthenticationService authenticateService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticateService = authenticateService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    private TokenResponse isLogged(@RequestBody UserAuthRequest userAuthRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userAuthRequest.getUsername(), userAuthRequest.getPassword()));
        return authenticateService.authenticate(userAuthRequest,authentication);
    }

    @PostMapping("/logout")
    String handleLogout(@RequestHeader(name = "Authorization") String authorization) {
        return authenticateService.logout(authorization.substring(7));
    }

    @PostMapping("/isExpired")
    TokenBooleanResponse isExpired(@RequestBody TokenRequest token) {
        return TokenBooleanResponse.builder().isExpired(!jwtService.validateExpiration(token.getToken())).build();
    }


}