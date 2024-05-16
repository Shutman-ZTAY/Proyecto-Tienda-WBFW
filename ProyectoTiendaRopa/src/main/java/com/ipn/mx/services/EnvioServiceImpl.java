package com.ipn.mx.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipn.mx.modelo.entidades.Envio;
import com.ipn.mx.modelo.repository.EnvioRepository;

@Service
public class EnvioServiceImpl implements EnvioServce {
	
	@Autowired
	EnvioRepository repo;

	@Override
	@Transactional(readOnly = false)
	public Envio create(Envio envio) {
		return repo.save(envio);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Long id) {
		repo.deleteById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public Envio update(Envio envio) {
		Envio e = repo.findById(envio.getIdEnvio()).orElse(null);
		if (e == null) {
			return null;
		}
		return repo.save(envio);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Envio> findAll() {
		return (List<Envio>) repo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Envio findById(Long id) {
		return repo.findById(id).orElse(null);
	}

}
