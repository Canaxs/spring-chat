package com.springchat.controller;

import com.springchat.models.request.UserAuthRequest;
import com.springchat.service.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticateService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationController(AuthenticationService authenticateService, AuthenticationManager authenticationManager) {
        this.authenticateService = authenticateService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    private String isLogged(@RequestBody UserAuthRequest userAuthRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userAuthRequest.getUsername(), userAuthRequest.getPassword()));
        return authenticateService.authenticate(userAuthRequest,authentication);
    }

    @PostMapping("/logout")
    String handleLogout(@RequestHeader(name = "Authorization") String authorization) {
        return authenticateService.logout(authorization.substring(7));
    }
}
