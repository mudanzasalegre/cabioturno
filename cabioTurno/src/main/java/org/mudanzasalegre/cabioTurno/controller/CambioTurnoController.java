package org.mudanzasalegre.cabioTurno.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.mudanzasalegre.cabioTurno.model.CambioTurno;
import org.mudanzasalegre.cabioTurno.model.Notificacion;
import org.mudanzasalegre.cabioTurno.model.Usuario;
import org.mudanzasalegre.cabioTurno.service.ICambioTurnoService;
import org.mudanzasalegre.cabioTurno.service.NotificacionService;
import org.mudanzasalegre.cabioTurno.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cambioTurno")
public class CambioTurnoController {

	@Autowired
	private ICambioTurnoService cambioTurnoService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private NotificacionService notificacionService;

	// Método para añadir notificaciones y otros atributos comunes al modelo
	private void addCommonAttributes(Model model, Usuario usuario) {
		List<String> roles = usuario.getPerfiles().stream().map(perfil -> String.valueOf(perfil.getId()))
				.collect(Collectors.toList());
		List<Notificacion> notificaciones = notificacionService.obtenerNotificaciones(usuario);

		model.addAttribute("roles", roles);
		model.addAttribute("username", usuario.getUsername());
		model.addAttribute("notifications", notificaciones);
		model.addAttribute("notificationCount", notificaciones.size());
	}

	@GetMapping("/form")
	public String mostrarFormulario(@AuthenticationPrincipal User user, Model model) {
		Usuario solicitante = usuarioRepository.findByUsername(user.getUsername());
		model.addAttribute("cambioTurno", new CambioTurno());
		model.addAttribute("solicitanteNombre", solicitante.getNombre());
		addCommonAttributes(model, solicitante);

		return "cambioTurno/formCambioTurno";
	}

	@PostMapping("/save")
	public String guardarCambioTurno(@ModelAttribute CambioTurno cambioTurno, BindingResult result,
			@AuthenticationPrincipal User user, Model model, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			model.addAttribute("solicitanteNombre", user.getUsername());
			model.addAttribute("cambioTurno", cambioTurno);
			model.addAttribute("errorMessage", "Error al guardar el cambio de turno. Por favor, verifica los datos ingresados.");
			return "cambioTurno/formCambioTurno";
		}

		Usuario usuarioActual = usuarioRepository.findByUsername(user.getUsername());
		Usuario solicitante = null;

		if (cambioTurno.getId() != null) {
			CambioTurno cambioTurnoExistente = cambioTurnoService.buscarPorId(cambioTurno.getId());
			if (cambioTurnoExistente != null) {
				solicitante = cambioTurnoExistente.getSolicitante();
				cambioTurno.setFechaSolicitud(cambioTurnoExistente.getFechaSolicitud()); // Mantener la fecha de solicitud existente
			}
		} else {
			solicitante = usuarioActual;
			cambioTurno.setSolicitante(solicitante);
			cambioTurno.setFechaSolicitud(LocalDateTime.now());
		}

		if (solicitante == null) {
			redirectAttributes.addFlashAttribute("errorMessage", "Solicitante no encontrado.");
			return "redirect:/cambioTurno/form";
		} else {
			cambioTurno.setSolicitante(solicitante);
		}

		if (cambioTurno.getEstado() == null || cambioTurno.getEstado().isEmpty()) {
			cambioTurno.setEstado("Pendiente");
		} else if (cambioTurno.getEstado().equals("Aceptado") || cambioTurno.getEstado().equals("Rechazado")) {
			cambioTurno.setFechaResolucion(LocalDateTime.now());
			notificacionService.enviarNotificacion("Cambio de Turno", "Tu cambio de turno ha sido ", solicitante,
					cambioTurno.getId().intValue(), cambioTurno.getEstado().toLowerCase());
		}

		cambioTurnoService.guardar(cambioTurno);

		// Enviar notificación
		notificacionService.enviarNotificacionATodosLosAdministradores("Cambio de turno", "Nuevo cambio de turno solicitado",
				cambioTurno.getId().intValue(), "Pendiente");

		boolean esAdmin = usuarioActual.getPerfiles().stream().anyMatch(perfil -> perfil.getNombre().equals("Administrador"));
		redirectAttributes.addFlashAttribute("successMessage", "Cambio de turno guardado exitosamente.");
		return esAdmin ? "redirect:/cambioTurno/list" : "redirect:/";
	}

	@GetMapping("/list")
	public String listarCambiosTurno(Model model, @AuthenticationPrincipal User user,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortField, @RequestParam(defaultValue = "asc") String sortDir) {
		Sort sort = Sort.by(sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
		Pageable pageable = PageRequest.of(page, size, sort);
		Page<CambioTurno> pageCambioTurno = cambioTurnoService.listarTodos(pageable);

		Usuario usuario = usuarioRepository.findByUsername(user.getUsername());

		model.addAttribute("cambiosTurno", pageCambioTurno.getContent());
		model.addAttribute("currentPage", pageCambioTurno.getNumber());
		model.addAttribute("totalPages", pageCambioTurno.getTotalPages());
		model.addAttribute("pageSize", pageCambioTurno.getSize());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		addCommonAttributes(model, usuario);

		return "cambioTurno/listCambioTurno";
	}

	@GetMapping("/edit/{id}")
	public String mostrarFormularioEdicion(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal User user) {
		CambioTurno cambioTurno = cambioTurnoService.buscarPorId(id);
		if (cambioTurno == null) {
			return "redirect:/cambioTurno/list";
		}
		Usuario solicitante = cambioTurno.getSolicitante();
		model.addAttribute("cambioTurno", cambioTurno);
		model.addAttribute("solicitanteNombre", solicitante.getNombre());

		// Verificar si el usuario es un administrador
		Usuario usuarioActual = usuarioRepository.findByUsername(user.getUsername());
		boolean esAdmin = usuarioActual.getPerfiles().stream().anyMatch(perfil -> perfil.getNombre().equals("Administrador"));
		model.addAttribute("esAdmin", esAdmin);

		addCommonAttributes(model, usuarioActual);

		return "cambioTurno/formCambioTurno";
	}

	@GetMapping("/delete/{id}")
	public String borrarCambioTurno(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		cambioTurnoService.borrar(id);
		redirectAttributes.addFlashAttribute("successMessage", "Cambio de turno borrado exitosamente.");
		return "redirect:/cambioTurno/list";
	}
}
