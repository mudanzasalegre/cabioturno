package org.mudanzasalegre.cabioTurno.service;

import java.time.LocalDateTime;
import java.util.List;

import org.mudanzasalegre.cabioTurno.config.NotificationWebSocketHandler;
import org.mudanzasalegre.cabioTurno.model.Notificacion;
import org.mudanzasalegre.cabioTurno.model.Usuario;
import org.mudanzasalegre.cabioTurno.repository.NotificacionRepository;
import org.mudanzasalegre.cabioTurno.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificacionService {

	@Autowired
	private NotificacionRepository notificacionRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private NotificationWebSocketHandler notificationWebSocketHandler;

	public void enviarNotificacion(String tipo, String descripcion, Usuario usuario, Integer referenciaId, String estado) {
		Notificacion notificacion = new Notificacion();
		notificacion.setTipo(tipo);
		notificacion.setDescripcion(descripcion);
		notificacion.setUsuario(usuario);
		notificacion.setReferenciaId(referenciaId);
		notificacion.setEstado(estado);
		notificacion.setFechaHora(LocalDateTime.now());

		notificacionRepository.save(notificacion);

		// Enviar notificación en tiempo real
		notificationWebSocketHandler.sendNotificationToUser(notificacion);
	}

	public void enviarNotificacionATodosLosAdministradores(String tipo, String descripcion, Integer referenciaId,
			String estado) {
		List<Usuario> administradores = usuarioRepository.findAllByPerfilesNombre("Administrador");
		for (Usuario admin : administradores) {
			enviarNotificacion(tipo, descripcion, admin, referenciaId, estado);
		}
	}

	public List<Notificacion> obtenerNotificaciones(Usuario usuario) {
		return notificacionRepository.findByUsuarioAndEstado(usuario, "Nueva");
	}

	public void marcarNotificacionesComoLeidas(String username) {
		Usuario usuario = usuarioRepository.findByUsername(username);
		List<Notificacion> notificaciones = notificacionRepository.findByUsuarioAndEstado(usuario, "Nueva");
		for (Notificacion notificacion : notificaciones) {
			notificacion.setEstado("Leída");
			notificacionRepository.save(notificacion);
		}
	}
}
