package org.mudanzasalegre.cabioTurno.repository;

import org.mudanzasalegre.cabioTurno.model.CambioTurno;
import org.mudanzasalegre.cabioTurno.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CambioTurnoRepository extends JpaRepository<CambioTurno, Long> {
	Page<CambioTurno> findBySolicitante(Usuario solicitante, Pageable pageable);
}
