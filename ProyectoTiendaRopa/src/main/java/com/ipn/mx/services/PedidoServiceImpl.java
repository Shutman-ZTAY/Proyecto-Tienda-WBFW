package com.ipn.mx.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipn.mx.modelo.entidades.Historial;
import com.ipn.mx.modelo.entidades.Pedido;
import com.ipn.mx.modelo.repository.HistorialRepository;
import com.ipn.mx.modelo.repository.PedidoRepository;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private HistorialRepository historialRepository;

    @Override
    public Pedido savePedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @Override
    public Optional<Pedido> findById(Long id) {
        return pedidoRepository.findById(id);
    }

    @Override
    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        pedidoRepository.deleteById(id);
    }

    @Transactional
    @Override
	public Pedido crearyRegistrarPedido(Pedido pedido) {
    	// Generar folio de compra
    	pedido.setFoliocompra(generateFolio(pedido));
    	// Guardar el pedido

    	// Crear y guardar el historial
    	Historial historial = new Historial();
    	historial.setFolioCompra(pedido.getFoliocompra());
    	LocalDateTime now = LocalDateTime.now();
    	Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
    	historial.setFecha(date);
    	Historial h = historialRepository.save(historial);
    	pedido.setHistorial(h);

    	pedido = pedidoRepository.save(pedido);
    	return pedido;
    }

    private String generateFolio(Pedido pedido) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    	return "FOLIO-" + LocalDateTime.now().format(formatter);
    }

}
