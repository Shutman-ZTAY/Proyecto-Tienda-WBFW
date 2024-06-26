package com.ipn.mx.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.ipn.mx.modelo.entidades.Promocion;
import com.ipn.mx.services.PdfReportService;
import com.ipn.mx.services.PromocionService;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class PromocionController {

    @Autowired
    private PromocionService promocionService;

    @Autowired
    private PdfReportService pdfReportService;

    @PostMapping("/promocion/insertar")
    public ResponseEntity<String> insertPromocion(@RequestBody Promocion promocion) {
        try {
            Promocion nuevaPromocion = promocionService.savePromocion(promocion);
            return ResponseEntity.status(HttpStatus.CREATED).body("Promoción insertada con ID " + nuevaPromocion.getIdpromocion());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo insertar la promoción");
        }
    }

    @GetMapping("/promociones")
    public List<Promocion> readAll() {
        return promocionService.findAll();
    }

    @GetMapping("/promocion/{id}")
    public ResponseEntity<Promocion> getPromocionById(@PathVariable Long id) {
        try {
            Promocion promocion = promocionService.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la promoción con ID: " + id));
            return ResponseEntity.ok().body(promocion);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al buscar la promoción con ID: " + id);
        }
    }

    @PutMapping("/promocion/actualizar/{id}")
    public ResponseEntity<String> actualizarPromocion(@PathVariable Long id, @RequestBody Promocion promocion) {
        Optional<Promocion> optionalPromocion = promocionService.findById(id);
        if (!optionalPromocion.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra promoción con ID " + id);
        }
        try {
            promocion.setIdpromocion(id);
            Promocion promocionActualizada = promocionService.savePromocion(promocion);
            return ResponseEntity.ok("Promoción actualizada correctamente con ID: " + promocionActualizada.getIdpromocion());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo actualizar la promoción");
        }
    }

    @DeleteMapping("/promocion/eliminar/{id}")
    public ResponseEntity<String> deletePromocion(@PathVariable Long id) {
        Optional<Promocion> optionalPromocion = promocionService.findById(id);
        if (!optionalPromocion.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra promoción con ID " + id);
        }
        try {
            promocionService.deleteById(id);
            return ResponseEntity.ok("Promoción con ID " + id + " eliminada");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo eliminar la promoción con ID " + id);
        }
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getReason());
    }

    @GetMapping("/promociones/reportepdf")
    public ResponseEntity<byte[]> generarReportePDF() {
        try {
            List<Promocion> promociones = promocionService.findAll();
            ByteArrayOutputStream pdfBytes = pdfReportService.generarReportePDFPromocion(promociones);
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=reporte_promociones.pdf")
                    .body(pdfBytes.toByteArray());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al generar el reporte PDF");
        }
    }
}

