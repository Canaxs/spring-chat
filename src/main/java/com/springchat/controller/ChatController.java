package com.springchat.controller;

import com.springchat.config.ChatConfig;
import com.springchat.config.WebSocketMetricLogger;
import com.springchat.models.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin
@RestController
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatConfig chatConfig;

    @Autowired
    private SimpUserRegistry simpUserRegistry;

    @MessageMapping("/chat")
    public void chatEndPoint(@Payload ChatMessage chatMessage) {
        System.out.println(chatMessage);
        messagingTemplate.convertAndSend("/topic",chatMessage);
    }

    @GetMapping("/numberSessions")
    private int getNumberOfSessions() {
        return simpUserRegistry.getUserCount();
    }
}
