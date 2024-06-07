package org.mudanzasalegre.cabioTurno.config;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class NotificationWebSocketHandler extends TextWebSocketHandler {

	private static final CopyOnWriteArraySet<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String username = session.getPrincipal().getName(); // Obtener el nombre de usuario del principal autenticado
		session.getAttributes().put("username", username); // Guardar el nombre de usuario en los atributos de la sesión
		sessions.add(session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessions.remove(session);
	}

	public void sendNotification(String username, String message) {
		for (WebSocketSession session : sessions) {
			String sessionUsername = (String) session.getAttributes().get("username");
			if (sessionUsername != null && sessionUsername.equals(username)) {
				try {
					session.sendMessage(new TextMessage(message));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// Maneja los mensajes recibidos y envía notificaciones a los clientes
		// conectados
		session.sendMessage(new TextMessage("Notificación recibida"));
	}
}
