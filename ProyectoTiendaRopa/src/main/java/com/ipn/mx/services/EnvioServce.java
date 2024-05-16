package com.ipn.mx.services;

import java.util.List;

import com.ipn.mx.modelo.entidades.Envio;

public interface EnvioServce {

	public Envio create(Envio envio);
	public void delete(Long id);
	public Envio update(Envio envio);
	public List<Envio> findAll();
	public Envio findById(Long id);
	
}
