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

import com.ipn.mx.modelo.entidades.Envio;
import com.ipn.mx.services.EnvioServce;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class EnvioController {

	@Autowired
	EnvioServce service;
	
	@ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getReason());
    }
	
	@GetMapping("/envios")
	public ResponseEntity<List<Envio>> readAll() {
		try {
			List<Envio> lp = service.findAll();
			return ResponseEntity.ok(lp);
        } catch (Exception e) {
        	throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor");
        }
    }
	
	@GetMapping("/envios/{id}")
	public ResponseEntity<Envio> readById(@PathVariable Long id) {
		try {
			Envio e = service.findById(id);
			if (e == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			return ResponseEntity.ok(e);
        } catch (Exception e) {
        	throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor");
        }
    }
	
	@PostMapping("/envios")
	public ResponseEntity<String> newEnvio(@RequestBody Envio envio) {
		try {
			Envio e = service.create(envio);
			return ResponseEntity.status(HttpStatus.CREATED).body("Pago insertado con ID " + e.getIdEnvio());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo insertar el pago");
		}
	}
	
	@DeleteMapping("/envios/{id}")
	public ResponseEntity<String> deletPago(@PathVariable Long id){
		try {
			Envio e = service.findById(id);
			if (e == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra pago con ID " + id);
			}
			service.delete(id);
			return ResponseEntity.ok("Envio con ID " + id + " eliminado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo eliminar el pago con ID " + id);
        }
	}
	
	@PutMapping("/envios/{id}")
    public ResponseEntity<String> updatePago(@PathVariable Long id, @RequestBody Envio envio) {
        try {
        	Envio e = service.findById(id);
            if (e == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra Pago con ID " + id);
            }
            envio.setIdEnvio(id);
            Envio newE = service.update(envio);
            return ResponseEntity.ok("Pago actualizado correctamente con ID: " + newE.getIdEnvio());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo actualizar el pago");
        }
	}
}
