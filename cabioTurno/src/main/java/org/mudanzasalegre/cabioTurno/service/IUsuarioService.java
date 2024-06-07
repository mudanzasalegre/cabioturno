package org.mudanzasalegre.cabioTurno.service;

import org.mudanzasalegre.cabioTurno.model.Usuario;

public interface IUsuarioService {
	Usuario buscarPorUsername(String username);
}
