package com.springchat.config;

import com.springchat.common.exception.ChatException;
import com.springchat.service.JwtService;
import com.springchat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.user.SimpSubscription;
import org.springframework.messaging.simp.user.SimpSubscriptionMatcher;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.messaging.DefaultSimpUserRegistry;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.util.*;

@Configuration
@EnableWebSocketMessageBroker
public class ChatConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtService jwtService;
    private final UserService userService;

    private final WebSocketMetricLogger webSocketMetricLogger;

    public ChatConfig(JwtService jwtService, UserService userService, Set<String> connectedSessionIds, WebSocketMetricLogger webSocketMetricLogger) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.webSocketMetricLogger = webSocketMetricLogger;
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(webSocketMetricLogger);
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        System.out.println(returnValueHandlers.size());
        WebSocketMessageBrokerConfigurer.super.addReturnValueHandlers(returnValueHandlers);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
    }
}
