
package com.socialmedia.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
         config.enableSimpleBroker("/topic");
        config.enableSimpleBroker("/room");
        config.setApplicationDestinationPrefixes("/app").setCacheLimit(1500000);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
        registry.setOrder(1500000);
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        // TODO Auto-generated method stub
        WebSocketMessageBrokerConfigurer.super.configureWebSocketTransport(registry);
        registry.setSendBufferSizeLimit(150000000)
                .setMessageSizeLimit(150000000);
    }






}
