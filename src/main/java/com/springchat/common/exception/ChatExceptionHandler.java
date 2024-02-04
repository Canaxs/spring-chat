package com.springchat.common.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ChatExceptionHandler {

    @ExceptionHandler({ChatException.class})
    public String chatException() {
        return "Chat Exception";
    }
}
