package com.ipn.mx.services;

import java.util.List;
import java.util.Optional;

import com.ipn.mx.modelo.entidades.Historial;

public interface HistorialService {
    Historial saveHistorial(Historial historial);
    Optional<Historial> findById(Long id);
    List<Historial> findAll();
    void deleteById(Long id);
}
