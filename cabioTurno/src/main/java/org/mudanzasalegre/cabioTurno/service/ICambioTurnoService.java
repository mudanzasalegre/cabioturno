package org.mudanzasalegre.cabioTurno.service;

import org.mudanzasalegre.cabioTurno.model.CambioTurno;
import org.mudanzasalegre.cabioTurno.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICambioTurnoService {
	void guardar(CambioTurno cambioTurno);

	Page<CambioTurno> listarTodos(Pageable pageable);

	CambioTurno buscarPorId(Long id);

	void borrar(Long id);

	Page<CambioTurno> listarPorSolicitante(Usuario solicitante, Pageable pageable);
}
