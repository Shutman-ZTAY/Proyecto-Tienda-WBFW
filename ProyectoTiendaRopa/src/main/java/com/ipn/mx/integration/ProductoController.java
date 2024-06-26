package com.ipn.mx.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.ipn.mx.modelo.entidades.Producto;
import com.ipn.mx.services.PdfReportService;
import com.ipn.mx.services.ProductoService;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private PdfReportService pdfReportService;

    @PostMapping("/producto/insertar")
    public ResponseEntity<String> insertProducto(@RequestBody Producto producto) {
        try {
            Producto nuevoProducto = productoService.saveProducto(producto);
            String mensaje = "Producto insertado con ID " + nuevoProducto.getIdProducto();
            return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo insertar el producto");
        }
    }

    @GetMapping("/productos")
    public List<Producto> readAll() {
        return productoService.findAll();
    }
    
    @ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
	    String mensaje = ex.getReason();
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensaje);
	}

    @GetMapping("/producto/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id) {
        try {
            Producto producto = productoService.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el producto con ID: " + id));
            return ResponseEntity.ok().body(producto);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al buscar el producto con ID: " + id);
        }
    }

    @PutMapping("/producto/actualizar/{id}")
    public ResponseEntity<String> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        Optional<Producto> optionalProducto = productoService.findById(id);
        if (!optionalProducto.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra producto con ID " + id);
        }
        try {
            producto.setIdProducto(id);
            Producto productoActualizado = productoService.saveProducto(producto);
            return ResponseEntity.ok("Producto actualizado correctamente: " + productoActualizado.getNombre());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo actualizar el producto");
        }
    }

    @DeleteMapping("/producto/eliminar/{id}")
    public ResponseEntity<String> deleteProducto(@PathVariable Long id) {
        Optional<Producto> optionalProducto = productoService.findById(id);
        if (!optionalProducto.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra producto con ID " + id);
        }
        try {
            productoService.deleteById(id);
            return ResponseEntity.ok("Producto con ID " + id + " eliminado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo eliminar el producto con ID " + id);
        }
    }

    @GetMapping("/producto/producto_aleatorio")
    public ResponseEntity<Producto> getProductoAleatorio() {
        try {
            List<Producto> productos = productoService.findAll();
            if (productos.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay productos disponibles");
            }
            Random random = new Random();
            Producto productoAleatorio = productos.get(random.nextInt(productos.size()));
            return ResponseEntity.ok().body(productoAleatorio);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener el producto aleatorio");
        }
    }

    @GetMapping("/productos/reportepdf")
    public ResponseEntity<byte[]> generarReportePDF() {
        try {
            List<Producto> productos = productoService.findAll();
            ByteArrayOutputStream pdfBytes = pdfReportService.generarReportePDFProducto(productos);
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=reporte_productos.pdf")
                    .body(pdfBytes.toByteArray());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al generar el reporte PDF");
        }
    }
}

