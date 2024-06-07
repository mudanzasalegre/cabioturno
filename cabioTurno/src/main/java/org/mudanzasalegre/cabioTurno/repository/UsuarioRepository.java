package org.mudanzasalegre.cabioTurno.repository;

import java.util.List;

import org.mudanzasalegre.cabioTurno.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	Usuario findByUsername(String username);

	List<Usuario> findAllByPerfilesNombre(String nombrePerfil);
}
