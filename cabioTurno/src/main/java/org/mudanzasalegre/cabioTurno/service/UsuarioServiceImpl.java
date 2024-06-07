package org.mudanzasalegre.cabioTurno.service;

import org.mudanzasalegre.cabioTurno.model.Usuario;
import org.mudanzasalegre.cabioTurno.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
}
