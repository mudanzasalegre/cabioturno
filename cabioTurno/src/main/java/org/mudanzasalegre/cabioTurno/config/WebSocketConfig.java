package org.mudanzasalegre.cabioTurno.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final ObjectMapper objectMapper;

    public WebSocketConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    NotificationWebSocketHandler notificationWebSocketHandler() {
        return new NotificationWebSocketHandler(objectMapper);
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(notificationWebSocketHandler(), "/ws/notifications")
                .setAllowedOrigins("*")
                .addInterceptors(new HttpSessionHandshakeInterceptor() {
                    @Override
                    public void afterHandshake(org.springframework.http.server.ServerHttpRequest request, 
                                               org.springframework.http.server.ServerHttpResponse response, 
                                               org.springframework.web.socket.WebSocketHandler wsHandler, 
                                               Exception ex) {
                        org.springframework.web.socket.WebSocketSession session = (org.springframework.web.socket.WebSocketSession) wsHandler;
                        String username = (String) session.getPrincipal().getName();
                        session.getAttributes().put("username", username);
                    }
                });
    }
}
