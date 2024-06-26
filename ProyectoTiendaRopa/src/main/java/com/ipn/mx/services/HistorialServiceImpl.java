package com.ipn.mx.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipn.mx.modelo.entidades.Historial;
import com.ipn.mx.modelo.repository.HistorialRepository;

import java.util.List;
import java.util.Optional;

@Service
public class HistorialServiceImpl implements HistorialService {

    @Autowired
    private HistorialRepository historialRepository;

    @Override
    public Historial saveHistorial(Historial historial) {
        return historialRepository.save(historial);
    }

    @Override
    public Optional<Historial> findById(Long id) {
        return historialRepository.findById(id);
    }

    @Override
    public List<Historial> findAll() {
        return historialRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        historialRepository.deleteById(id);
    }
}
