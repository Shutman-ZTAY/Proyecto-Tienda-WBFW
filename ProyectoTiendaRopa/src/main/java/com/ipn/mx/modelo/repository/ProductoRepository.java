package com.ipn.mx.modelo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ipn.mx.modelo.entidades.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

}
