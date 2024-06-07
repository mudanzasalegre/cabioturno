package org.mudanzasalegre.cabioTurno.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.mudanzasalegre.cabioTurno.model.Notificacion;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

public class NotificationWebSocketHandler extends TextWebSocketHandler {

	private final ObjectMapper objectMapper;
	private final List<WebSocketSession> sessions = new ArrayList<>();

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
		String payload;
		try {
			payload = objectMapper.writeValueAsString(notification);
		} catch (IOException e) {
			throw new RuntimeException("Error serializing notification", e);
		}

		TextMessage message = new TextMessage(payload);
		sessions.forEach(session -> {
			try {
				session.sendMessage(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
}
