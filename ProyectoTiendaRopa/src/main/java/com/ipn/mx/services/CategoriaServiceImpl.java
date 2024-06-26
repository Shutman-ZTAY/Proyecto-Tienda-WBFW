package com.ipn.mx.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipn.mx.modelo.entidades.Categoria;
import com.ipn.mx.modelo.repository.CategoriaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;


    @Override
    public Optional<Categoria> findById(Long id) {
        return categoriaRepository.findById(id);
    }

    @Override
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        categoriaRepository.deleteById(id);
    }

	
	@Transactional
    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

}
