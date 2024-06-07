package org.mudanzasalegre.cabioTurno.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.mudanzasalegre.cabioTurno.model.Notificacion;
import org.mudanzasalegre.cabioTurno.model.Usuario;
import org.mudanzasalegre.cabioTurno.service.IUsuarioService;
import org.mudanzasalegre.cabioTurno.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HomeController {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private NotificacionService notificacionService;

    @ModelAttribute
    public void addAttributes(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth.getPrincipal() instanceof String)) {
            String username = auth.getName();
            Usuario usuario = usuarioService.buscarPorUsername(username);
            List<String> roles = usuario.getPerfiles().stream()
                                        .map(perfil -> String.valueOf(perfil.getId()))
                                        .collect(Collectors.toList());
            List<Notificacion> notificaciones = notificacionService.obtenerNotificaciones(usuario);

            model.addAttribute("username", username);
            model.addAttribute("roles", roles);
            model.addAttribute("notifications", notificaciones);
            model.addAttribute("notificationCount", notificaciones.size());
        } else {
            model.addAttribute("roles", List.of());
        }
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/home")
    public String home() {
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/logout-success")
    public String logout(Model model) {
        model.addAttribute("logout", true);
        return "login";
    }
}
