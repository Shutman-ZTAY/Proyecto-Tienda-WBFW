package com.ipn.mx.services;


import java.util.List;
import java.util.Optional;

import com.ipn.mx.modelo.entidades.Categoria;

public interface CategoriaService {
    Optional<Categoria> findById(Long id);
    List<Categoria> findAll();
    void deleteById(Long id);
    Categoria save(Categoria categoria);
}
