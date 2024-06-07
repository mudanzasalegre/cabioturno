package org.mudanzasalegre.cabioTurno.controller;

import org.mudanzasalegre.cabioTurno.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @PostMapping("/marcarNotificacionesComoLeidas")
    public void marcarNotificacionesComoLeidas(@AuthenticationPrincipal User user) {
        if (user != null) {
            notificacionService.marcarNotificacionesComoLeidas(user.getUsername());
        } else {
            // Log para depuración
            System.out.println("Usuario no autenticado");
        }
    }
}
