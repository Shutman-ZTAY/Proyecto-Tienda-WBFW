package com.ipn.mx.services;


import java.util.List;
import java.util.Optional;

import com.ipn.mx.modelo.entidades.Promocion;


public interface PromocionService {
    Promocion savePromocion(Promocion promocion);
    Optional<Promocion> findById(Long id);
    List<Promocion> findAll();
    void deleteById(Long id);
}
