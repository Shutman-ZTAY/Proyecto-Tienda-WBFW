package com.ipn.mx.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.ipn.mx.modelo.entidades.Cliente;
import com.ipn.mx.services.ClienteService;
import com.ipn.mx.services.PdfReportService;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private PdfReportService pdfReportService;

    @PostMapping("/cliente/insertar")
    public ResponseEntity<String> insertCliente(@RequestBody Cliente cliente) {
        try {
            Cliente nuevoCliente = clienteService.saveCliente(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body("Cliente insertado con ID " + nuevoCliente.getIdcliente());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo insertar el cliente");
        }
    }

    @GetMapping("/clientes")
    public List<Cliente> readAll() {
        return clienteService.findAll();
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        try {
            Cliente cliente = clienteService.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el cliente con ID: " + id));
            return ResponseEntity.ok().body(cliente);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al buscar el cliente con ID: " + id);
        }
    }

    @PutMapping("/cliente/actualizar/{id}")
    public ResponseEntity<String> actualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        Optional<Cliente> optionalCliente = clienteService.findById(id);
        if (!optionalCliente.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra cliente con ID " + id);
        }
        try {
            cliente.setIdcliente(id);
            Cliente clienteActualizado = clienteService.saveCliente(cliente);
            return ResponseEntity.ok("Cliente actualizado correctamente con ID: " + clienteActualizado.getIdcliente());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo actualizar el cliente");
        }
    }

    @DeleteMapping("/cliente/eliminar/{id}")
    public ResponseEntity<String> deleteCliente(@PathVariable Long id) {
        Optional<Cliente> optionalCliente = clienteService.findById(id);
        if (!optionalCliente.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra cliente con ID " + id);
        }
        try {
            clienteService.deleteById(id);
            return ResponseEntity.ok("Cliente con ID " + id + " eliminado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo eliminar el cliente con ID " + id);
        }
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getReason());
    }

    @GetMapping("/clientes/reportepdf")
    public ResponseEntity<byte[]> generarReportePDF() {
        try {
            List<Cliente> clientes = clienteService.findAll();
            ByteArrayOutputStream pdfBytes = pdfReportService.generarReportePDFCliente(clientes);
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=reporte_clientes.pdf")
                    .body(pdfBytes.toByteArray());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al generar el reporte PDF");
        }
    }
}
