package com.springchat.controller;

import com.springchat.models.request.UserCreateRequest;
import com.springchat.models.response.ResultResponse;
import com.springchat.models.response.UserCreateResponse;
import com.springchat.models.response.UsernameResponse;
import com.springchat.persistence.entity.User;
import com.springchat.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "https://spring-chat-ui.vercel.app",allowCredentials = "true")
@RequestMapping("/user")
public class UserController {

    private UserService userService;


    public UserController(UserService userService) {

        this.userService = userService;
    }

    @PostMapping("/create")
    private ResponseEntity<UserCreateResponse> userCreate(@RequestBody UserCreateRequest userCreateRequest) {
        User user = userService.createUser(userCreateRequest);
        return ResponseEntity.ok(UserCreateResponse.builder().id(String.valueOf(user.getId())).username(user.getUsername()).password(user.getPassword()).build());
    }

    @GetMapping("/username")
    private ResponseEntity<UsernameResponse> getUsername() {
        return ResponseEntity.ok(userService.getUsername());
    }
}
