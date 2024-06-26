package com.ipn.mx.services;


import java.util.List;
import java.util.Optional;

import com.ipn.mx.modelo.entidades.Pedido;

public interface PedidoService {
    Pedido savePedido(Pedido pedido);
    Optional<Pedido> findById(Long id);
    List<Pedido> findAll();
    void deleteById(Long id);
    Pedido crearyRegistrarPedido(Pedido pedido);
}
