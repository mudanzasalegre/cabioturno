package org.mudanzasalegre.cabioTurno.config;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.mudanzasalegre.cabioTurno.model.Notificacion;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class NotificationWebSocketHandler extends TextWebSocketHandler {

	private final Set<WebSocketSession> sessions = new HashSet<>();
	private final ObjectMapper objectMapper;

	public NotificationWebSocketHandler(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		sessions.add(session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
		sessions.remove(session);
	}

	public void sendNotificationToUser(Notificacion notification) {
		try {
			String notificationJson = objectMapper.writeValueAsString(notification);
			for (WebSocketSession session : sessions) {
				session.sendMessage(new TextMessage(notificationJson));
			}
		} catch (IOException e) {
			throw new RuntimeException("Error converting notification to JSON", e);
		}
	}
}
