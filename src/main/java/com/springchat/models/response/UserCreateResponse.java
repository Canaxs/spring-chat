package com.springchat.models.response;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class UserCreateResponse {
    private String id;
    private String username;
    private String password;
}
