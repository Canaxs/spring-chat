package com.springchat.config;

import com.springchat.common.exception.ChatException;
import com.springchat.service.JwtService;
import com.springchat.service.UserService;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Component
public class WebSocketMetricLogger implements ChannelInterceptor {

    private final JwtService jwtService;
    private final UserService userService;

    public final Set<String> connectedSessionIds;

    private Boolean auth = false;

    public WebSocketMetricLogger(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.connectedSessionIds = Collections.synchronizedSet(new HashSet<>());
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if(accessor != null && (StompCommand.CONNECT.equals(accessor.getCommand()) || StompCommand.SEND.equals(accessor.getCommand()))) {
            String token = (Objects.requireNonNull(accessor.getFirstNativeHeader("Authorization"))).substring(7);
            String userName = jwtService.extractUser(token);
            Boolean isExpired = !jwtService.validateExpiration(token);
            if (userName != null && auth == false && !isExpired) {
                UserDetails user = userService.loadUserByUsername(userName);
                if (jwtService.validateToken(token, user)) {
                    connectedSessionIds.add(accessor.getSessionId());
                    System.out.println(accessor.getSessionId()+" ve: "+connectedSessionIds.size());
                    auth = true;
                }
                else {
                    throw new ChatException("");
                }
            }
            else {
                if(auth == false || isExpired) {
                    throw new ChatException("");
                }
            }

        }
        else {
            if(StompCommand.DISCONNECT.equals(accessor.getCommand())) {
                connectedSessionIds.remove(accessor.getSessionId());
            }
            if(auth == false) {
                throw new ChatException("");
            }

        }

        return ChannelInterceptor.super.preSend(message, channel);
    }
}
