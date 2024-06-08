package org.mudanzasalegre.cabioTurno.repository;

import java.util.List;

import org.mudanzasalegre.cabioTurno.model.Notificacion;
import org.mudanzasalegre.cabioTurno.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
	List<Notificacion> findByUsuarioAndEstado(Usuario usuario, String estado);
}