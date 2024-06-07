package org.mudanzasalegre.cabioTurno.controller;

import java.util.List;

import org.mudanzasalegre.cabioTurno.model.Notificacion;
import org.mudanzasalegre.cabioTurno.model.Usuario;
import org.mudanzasalegre.cabioTurno.repository.UsuarioRepository;
import org.mudanzasalegre.cabioTurno.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificacionController {

	@Autowired
	private NotificacionService notificacionService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@GetMapping("/notificaciones")
	public ResponseEntity<List<Notificacion>> obtenerNotificaciones(@AuthenticationPrincipal User user) {
		if (user != null) {
			return ResponseEntity
					.ok(notificacionService.obtenerNotificaciones(usuarioRepository.findByUsername(user.getUsername())));
		} else {
			// Log para depuración
			System.out.println("Usuario no autenticado");
			return ResponseEntity.status(401).build();
		}
	}

	@PostMapping("/marcarNotificacionesComoLeidas")
	@ResponseBody
	public ResponseEntity<?> marcarNotificacionesComoLeidas(@AuthenticationPrincipal User user) {
	    Usuario usuario = usuarioRepository.findByUsername(user.getUsername());
	    notificacionService.marcarNotificacionesComoLeidas(usuario.getUsername());
	    return ResponseEntity.ok().build();
	}


}
