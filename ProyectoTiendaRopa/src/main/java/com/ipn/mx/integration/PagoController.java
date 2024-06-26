package com.ipn.mx.integration;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ipn.mx.modelo.entidades.Pago;
import com.ipn.mx.services.PagoService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class PagoController {

	@Autowired
	PagoService service;
	
	@ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getReason());
    }
	
	@GetMapping("/pagos")
	public ResponseEntity<List<Pago>> readAll() {
		try {
			List<Pago> lp = service.findAll();
			return ResponseEntity.ok(lp);
        } catch (Exception e) {
        	throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor");
        }
    }
	
	@GetMapping("/pagos/{id}")
	public ResponseEntity<Pago> readById(@PathVariable Long id) {
		try {
			Pago p = service.findById(id);
			if (p == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			return ResponseEntity.ok(p);
        } catch (Exception e) {
        	throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor");
        }
    }
	
	@PostMapping("/pagos")
	public ResponseEntity<String> newPago(@RequestBody Pago pago) {
		try {
			Pago p = service.create(pago);
			return ResponseEntity.status(HttpStatus.CREATED).body("Pago insertado con ID " + p.getIdPago());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo insertar el pago");
		}
	}
	
	@DeleteMapping("/pagos/{id}")
	public ResponseEntity<String> deletPago(@PathVariable Long id){
		try {
			Pago p = service.findById(id);
			if (p == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra pago con ID " + id);
			}
			service.delete(id);
			return ResponseEntity.ok("Pago con ID " + id + " eliminado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo eliminar el pago con ID " + id);
        }
	}
	
	@PutMapping("/pagos/{id}")
    public ResponseEntity<String> updatePago(@PathVariable Long id, @RequestBody Pago pago) {
        try {
        	Pago p = service.findById(id);
            if (p == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra Pago con ID " + id);
            }
            pago.setIdPago(id);
            Pago newP = service.update(pago);
            return ResponseEntity.ok("Pago actualizado correctamente con ID: " + newP.getIdPago());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo actualizar el pago");
        }
    }
	
}
