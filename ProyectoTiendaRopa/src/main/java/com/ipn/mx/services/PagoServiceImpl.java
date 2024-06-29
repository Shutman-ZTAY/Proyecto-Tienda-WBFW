package com.ipn.mx.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipn.mx.modelo.entidades.Pago;
import com.ipn.mx.modelo.repository.PagoRepository;

@Service
public class PagoServiceImpl implements PagoService {

	@Autowired
	PagoRepository repo;
	
	@Override
	@Transactional(readOnly = false)
	public Pago create(Pago pago) {
		return repo.save(pago);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Long id) {
		repo.deleteById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public Pago update(Pago pago) {
		if (!repo.existsById(pago.getIdPago())) {
			return null;
		}
		return repo.save(pago);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Pago> findAll() {
		return (List<Pago>) repo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Pago findById(Long id) {
		return repo.findById(id).orElse(null);
	}

}
