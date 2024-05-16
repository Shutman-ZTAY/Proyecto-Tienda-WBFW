package com.ipn.mx.services;

import java.util.List;

import com.ipn.mx.modelo.entidades.Pago;

public interface PagoService {

	public Pago create(Pago pago);
	public void delete(Long id);
	public Pago update(Pago pago);
	public List<Pago> findAll();
	public Pago findById(Long id);
}
