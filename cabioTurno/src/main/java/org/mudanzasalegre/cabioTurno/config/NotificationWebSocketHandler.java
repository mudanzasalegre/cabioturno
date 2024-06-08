package org.mudanzasalegre.cabioTurno.config;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.mudanzasalegre.cabioTurno.model.Notificacion;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

public class NotificationWebSocketHandler extends TextWebSocketHandler {

	private final ObjectMapper objectMapper;
	private final ConcurrentMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

	public NotificationWebSocketHandler(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		String username = (String) session.getAttributes().get("username");
		if (username != null) {
			sessions.put(username, session);
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
		String username = (String) session.getAttributes().get("username");
		if (username != null) {
			sessions.remove(username);
		}
	}

	public void sendNotificationToUser(Notificacion notification) {
		String username = notification.getUsuario().getUsername();
		WebSocketSession session = sessions.get(username);

		if (session != null && session.isOpen()) {
			try {
				String payload = objectMapper.writeValueAsString(notification);
				session.sendMessage(new TextMessage(payload));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
