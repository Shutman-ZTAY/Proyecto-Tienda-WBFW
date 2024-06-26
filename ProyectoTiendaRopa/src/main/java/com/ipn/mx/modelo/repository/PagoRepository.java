package com.ipn.mx.modelo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ipn.mx.modelo.entidades.Pago;

@Repository
public interface PagoRepository extends CrudRepository<Pago, Long> {

}
