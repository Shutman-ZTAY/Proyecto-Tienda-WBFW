package com.ipn.mx.modelo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ipn.mx.modelo.entidades.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
 
}