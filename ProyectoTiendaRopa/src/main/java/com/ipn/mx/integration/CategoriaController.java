package com.ipn.mx.integration;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.ipn.mx.modelo.entidades.Categoria;
import com.ipn.mx.services.CategoriaService;
import com.ipn.mx.services.PdfReportService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private PdfReportService pdfReportService;

    @PostMapping("/categoria/insertar")
	public ResponseEntity<String> insertCategoria(@RequestBody Categoria categoria) {
	    try {
	        Categoria nuevaCategoria = categoriaService.save(categoria);
	        String mensaje = "Categoría insertada con ID " + nuevaCategoria.getIdCategoria();
	        return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
	    } catch (Exception e) {
	        String mensajeError = "No se pudo insertar la categoría";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensajeError);
	    }
	}

    @GetMapping("/categorias")
    public List<Categoria> readAll() {
        return categoriaService.findAll();
    }
    
    @ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
	    String mensaje = ex.getReason();
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensaje);
	}

    @GetMapping("/categoria/{id}")
	public ResponseEntity<Categoria> getCategoriaById(@PathVariable Long id) {
	    try {
	        Categoria categoria = categoriaService.findById(id)
	                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la categoría con ID: " + id));
	        return ResponseEntity.ok().body(categoria);
	    } catch (Exception e) {
	        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al buscar la categoría con ID: " + id);
	    }
	}

    @PutMapping("/categoria/actualizar/{id}")
	public ResponseEntity<String> actualizarCategoria(@PathVariable Long id, @RequestBody Categoria categoria) {
	    // Verificar si la categoría con el ID especificado existe
	    Optional<Categoria> optionalCategoria = categoriaService.findById(id);
	    if (!optionalCategoria.isPresent()) {
	        String mensaje = "No se encuentra categoría con ID " + id;
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensaje);
	    }

	    try {
	        categoria.setIdCategoria(id);
	        Categoria categoriaActualizada = categoriaService.save(categoria);
	        String mensaje = "Categoría actualizada correctamente: " + categoriaActualizada.getNombre();
	        return ResponseEntity.ok().body(mensaje);
	    } catch (Exception e) {
	        String mensajeError = "No se pudo actualizar la categoría";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensajeError);
	    }
	}

    
    @DeleteMapping("/categoria/eliminar/{id}")
	public ResponseEntity<String> deleteCategoria(@PathVariable Long id) {
	    // Verificar si la categoría con el ID especificado existe
	    Optional<Categoria> optionalCategoria = categoriaService.findById(id);
	    if (!optionalCategoria.isPresent()) {
	        String mensaje = "No se encuentra categoría con ID " + id;
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensaje);
	    }

	    // La categoría existe, entonces intenta eliminarla
	    try {
	    	categoriaService.deleteById(id);
	        String mensaje = "Categoría con ID " + id + " eliminada";
	        return ResponseEntity.ok(mensaje);
	    } catch (Exception e) {
	        String mensajeError = "No se pudo eliminar la categoría con ID " + id;
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensajeError);
	    }
	}
    
    
    
    
    
    
    

	@GetMapping("/categoria/categoria_aleatoria")
	public ResponseEntity<Categoria> getCategoriaAleatoria() {
	    try {
	        List<Categoria> categorias = categoriaService.findAll();
	        if (categorias.isEmpty()) {
	            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay categorías disponibles");
	        }

	        Random random = new Random();
	        int index = random.nextInt(categorias.size());

	        Categoria categoriaAleatoria = categorias.get(index);

	        return ResponseEntity.ok().body(categoriaAleatoria);
	    } catch (Exception e) {
	        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener la categoría aleatoria");
	    }
	}
	
	
	

	@GetMapping("/categorias/reportepdf")
	public ResponseEntity<byte[]> generarReportePDF() {
	    List<Categoria> categorias = categoriaService.findAll();
	    ByteArrayOutputStream pdfBytes = pdfReportService.generarReportePDFCategoria(categorias);
	    return ResponseEntity.ok()
	            .header("Content-Disposition", "attachment; filename=reporte_categorias.pdf")
	            .body(pdfBytes.toByteArray());
	}
    
    
    
}