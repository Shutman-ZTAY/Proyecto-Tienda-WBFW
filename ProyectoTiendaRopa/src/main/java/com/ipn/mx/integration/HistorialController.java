package com.ipn.mx.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.ipn.mx.modelo.entidades.Historial;
import com.ipn.mx.services.HistorialService;
import com.ipn.mx.services.PdfReportService;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class HistorialController {

    @Autowired
    private HistorialService historialService;

    @Autowired
    private PdfReportService pdfReportService;

    @PostMapping("/historial/insertar")
    public ResponseEntity<String> insertHistorial(@RequestBody Historial historial) {
        try {
            Historial nuevoHistorial = historialService.saveHistorial(historial);
            return ResponseEntity.status(HttpStatus.CREATED).body("Historial insertado con ID " + nuevoHistorial.getIdhistorial());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo insertar el historial");
        }
    }

    @GetMapping("/historiales")
    public List<Historial> readAll() {
        return historialService.findAll();
    }

    @GetMapping("/historial/{id}")
    public ResponseEntity<Historial> getHistorialById(@PathVariable Long id) {
        try {
            Historial historial = historialService.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el historial con ID: " + id));
            return ResponseEntity.ok().body(historial);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al buscar el historial con ID: " + id);
        }
    }

    @PutMapping("/historial/actualizar/{id}")
    public ResponseEntity<String> actualizarHistorial(@PathVariable Long id, @RequestBody Historial historial) {
        Optional<Historial> optionalHistorial = historialService.findById(id);
        if (!optionalHistorial.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra historial con ID " + id);
        }
        try {
            historial.setIdhistorial(id);
            Historial historialActualizado = historialService.saveHistorial(historial);
            return ResponseEntity.ok("Historial actualizado correctamente con ID: " + historialActualizado.getIdhistorial());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo actualizar el historial");
        }
    }

    @DeleteMapping("/historial/eliminar/{id}")
    public ResponseEntity<String> deleteHistorial(@PathVariable Long id) {
        Optional<Historial> optionalHistorial = historialService.findById(id);
        if (!optionalHistorial.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra historial con ID " + id);
        }
        try {
            historialService.deleteById(id);
            return ResponseEntity.ok("Historial con ID " + id + " eliminado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo eliminar el historial con ID " + id);
        }
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getReason());
    }

    @GetMapping("/historiales/reportepdf")
    public ResponseEntity<byte[]> generarReportePDF() {
        try {
            List<Historial> historiales = historialService.findAll();
            ByteArrayOutputStream pdfBytes = pdfReportService.generarReportePDFHistorial(historiales);
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=reporte_historiales.pdf")
                    .body(pdfBytes.toByteArray());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al generar el reporte PDF");
        }
    }
}

