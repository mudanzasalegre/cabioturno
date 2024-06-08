package org.mudanzasalegre.cabioTurno.service;

import org.mudanzasalegre.cabioTurno.model.CambioTurno;
import org.mudanzasalegre.cabioTurno.model.Usuario;
import org.mudanzasalegre.cabioTurno.repository.CambioTurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CambioTurnoServiceImpl implements ICambioTurnoService {

    @Autowired
    private CambioTurnoRepository cambioTurnoRepository;

    @Override
    public void guardar(CambioTurno cambioTurno) {
        cambioTurnoRepository.save(cambioTurno);
    }

    @Override
    public Page<CambioTurno> listarTodos(Pageable pageable) {
        return cambioTurnoRepository.findAll(pageable);
    }

    @Override
    public CambioTurno buscarPorId(Long id) {
        return cambioTurnoRepository.findById(id).orElse(null);
    }

    @Override
    public void borrar(Long id) {
        cambioTurnoRepository.deleteById(id);
    }
    
    @Override
    public Page<CambioTurno> listarPorSolicitante(Usuario solicitante, Pageable pageable) {
        return cambioTurnoRepository.findBySolicitante(solicitante, pageable);
    }
}
