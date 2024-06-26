package com.ipn.mx.services;

import java.util.List;
import java.util.Optional;

import com.ipn.mx.modelo.entidades.Cliente;


public interface ClienteService {
    Cliente saveCliente(Cliente cliente);
    Optional<Cliente> findById(Long id);
    List<Cliente> findAll();
    void deleteById(Long id);
}
