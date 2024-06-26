package com.ipn.mx.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipn.mx.modelo.entidades.Promocion;
import com.ipn.mx.modelo.repository.PromocionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PromocionServiceImpl implements PromocionService {

    @Autowired
    private PromocionRepository promocionRepository;

    @Override
    public Promocion savePromocion(Promocion promocion) {
        return promocionRepository.save(promocion);
    }

    @Override
    public Optional<Promocion> findById(Long id) {
        return promocionRepository.findById(id);
    }

    @Override
    public List<Promocion> findAll() {
        return promocionRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        promocionRepository.deleteById(id);
    }

}
