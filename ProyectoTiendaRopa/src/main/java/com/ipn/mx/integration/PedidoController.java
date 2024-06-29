package com.ipn.mx.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.ipn.mx.modelo.entidades.Pedido;
import com.ipn.mx.modelo.entidades.Producto;
import com.ipn.mx.services.PdfReportService;
import com.ipn.mx.services.PedidoService;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PdfReportService pdfReportService;

    @PostMapping("/pedido/insertar")
    public ResponseEntity<String> insertPedido(@RequestBody Pedido pedido) {
        try {
        	System.out.println(pedido);
            Pedido nuevoPedido = pedidoService.crearyRegistrarPedido(pedido);
            return ResponseEntity.status(HttpStatus.CREATED).body("Pedido insertado con ID " + nuevoPedido.getIdpedido());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/pedidos")
    public List<Pedido> readAll() {
        return pedidoService.findAll();
    }

    @GetMapping("/pedido/{id}")
    public ResponseEntity<Pedido> getPedidoById(@PathVariable Long id) {
        try {
            Pedido pedido = pedidoService.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el pedido con ID: " + id));
            return ResponseEntity.ok().body(pedido);
        } catch (Exception e) {
        	System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PutMapping("/pedido/actualizar/{id}")
    public ResponseEntity<String> actualizarPedido(@PathVariable Long id, @RequestBody Pedido pedido) {
        Pedido optionalPedido = pedidoService.findById(id).orElse(null);
        if (optionalPedido == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra pedido con ID " + id);
        }
        try {
            pedido.setIdpedido(id);
            pedido.setCliente(optionalPedido.getCliente());
            pedido.setHistorial(optionalPedido.getHistorial());	
            pedido.setProductos(optionalPedido.getProductos());
            Pedido pedidoActualizado = pedidoService.savePedido(pedido);
            return ResponseEntity.ok("Pedido actualizado correctamente con ID: " + pedidoActualizado.getIdpedido());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo actualizar el pedido: " + e.getMessage());
        }
    }

    @DeleteMapping("/pedido/eliminar/{id}")
    public ResponseEntity<String> deletePedido(@PathVariable Long id) {
        Optional<Pedido> optionalPedido = pedidoService.findById(id);
        if (!optionalPedido.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra pedido con ID " + id);
        }
        try {
            pedidoService.deleteById(id);
            return ResponseEntity.ok("Pedido con ID " + id + " eliminado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo eliminar el pedido con ID " + id);
        }
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getReason());
    }

    @GetMapping("/pedidos/reportepdf")
    public ResponseEntity<byte[]> generarReportePDF() {
        try {
            List<Pedido> pedidos = pedidoService.findAll();
            ByteArrayOutputStream pdfBytes = pdfReportService.generarReportePDFPedidos(pedidos);
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=reporte_pedidos.pdf")
                    .body(pdfBytes.toByteArray());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al generar el reporte PDF");
        }
    }
}

