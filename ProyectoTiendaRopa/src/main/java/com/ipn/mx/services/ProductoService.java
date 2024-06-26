package com.ipn.mx.services;

import java.util.List;
import java.util.Optional;

import com.ipn.mx.modelo.entidades.Producto;

public interface ProductoService {
    Producto saveProducto(Producto producto);
    Optional<Producto> findById(Long id);
    List<Producto> findAll();
    void deleteById(Long id);
}

