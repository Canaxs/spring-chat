package com.springchat.controller;

import com.springchat.models.request.UserCreateRequest;
import com.springchat.models.response.ResultResponse;
import com.springchat.models.response.UserCreateResponse;
import com.springchat.persistence.entity.User;
import com.springchat.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;


    public UserController(UserService userService) {

        this.userService = userService;
    }

    @PostMapping("/create")
    private ResponseEntity<ResultResponse> userCreate(@RequestBody UserCreateRequest userCreateRequest) {
        ResultResponse resultResponse = new ResultResponse();
        List<UserCreateResponse> createResponses = new ArrayList<>();
        User user = userService.createUser(userCreateRequest);
        createResponses.add(UserCreateResponse.builder().id(String.valueOf(user.getId())).username(user.getUsername()).password(user.getPassword()).build());
        resultResponse.setResult(createResponses);
        return ResponseEntity.ok(resultResponse);
    }
}
