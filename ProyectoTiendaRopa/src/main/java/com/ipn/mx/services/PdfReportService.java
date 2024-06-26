package com.ipn.mx.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.ipn.mx.modelo.entidades.Categoria;
import com.ipn.mx.modelo.entidades.Cliente;
import com.ipn.mx.modelo.entidades.Historial;
import com.ipn.mx.modelo.entidades.Pedido;
import com.ipn.mx.modelo.entidades.Producto;
import com.ipn.mx.modelo.entidades.Promocion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfReportService {

    @Autowired
    private CategoriaService categoriaService;
    
    @Autowired
    private ProductoService productoService;

    public ByteArrayOutputStream generarReportePDFCategoria(List<Categoria> categorias) {
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            Font fontTitle = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLUE);
            Paragraph title = new Paragraph("Lista de Categorías", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9);
            PdfPTable table = new PdfPTable(3);

            addHeaderCell(table, "ID clave de la categoria", fontHeader);
            addHeaderCell(table, "Nombre de la categoria", fontHeader);
            addHeaderCell(table, "Tipo de categoria", fontHeader);

            for (Categoria categoria : categorias) {
                addCell(table, String.valueOf(categoria.getIdCategoria()));
                addCell(table, categoria.getNombre());
                addCell(table, categoria.getTipo());
            }

            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return outputStream;
    }

    
    public ByteArrayOutputStream generarReportePDFProducto(List<Producto> productos) {
    	Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            Font fontTitle = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLUE);
            Paragraph title = new Paragraph("Lista de Productos", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9);
            PdfPTable table = new PdfPTable(8);

            addHeaderCell(table, "ID del producto", fontHeader);
            addHeaderCell(table, "Nombre del producto", fontHeader);
            addHeaderCell(table, "Descripción", fontHeader);
            addHeaderCell(table, "Precio", fontHeader);
            addHeaderCell(table, "Tamaño", fontHeader);
            addHeaderCell(table, "Color", fontHeader);
            addHeaderCell(table, "Disponibilidad", fontHeader);
            addHeaderCell(table, "Id Categoria", fontHeader);

            for (Producto producto : productos) {
                addCell(table, String.valueOf(producto.getIdProducto()));
                addCell(table, producto.getNombre());
                addCell(table, producto.getDescripcion());
                addCell(table, String.valueOf(producto.getPrecio()));
                addCell(table, producto.getTamaño());
                addCell(table, producto.getColor());
                addCell(table, String.valueOf(producto.getDisponibilidad()));
                addCell(table, String.valueOf(producto.getCategoria().getIdCategoria()));
            }

            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return outputStream;
	}

    
    
    
    public ByteArrayOutputStream generarReportePDFHistorial(List<Historial> historiales) {
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            Font fontTitle = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLUE);
            Paragraph title = new Paragraph("Lista de Historial", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9);
            PdfPTable table = new PdfPTable(3);

            addHeaderCell(table, "ID", fontHeader);
            addHeaderCell(table, "Folio", fontHeader);
            addHeaderCell(table, "Fecha", fontHeader);

            for (Historial historial : historiales) {
                addCell(table, String.valueOf(historial.getIdhistorial()));
                addCell(table, historial.getFolioCompra());
                addCell(table, String.valueOf(historial.getFecha()));
            }

            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return outputStream;
    }
    
    
    
    
    
    
    public ByteArrayOutputStream generarReportePDFPedidos(List<Pedido> pedidos) {
    	Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            Font fontTitle = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLUE);
            Paragraph title = new Paragraph("Lista de Pedidos", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8);
            PdfPTable table = new PdfPTable(11);

            addHeaderCell(table, "ID del pedido", fontHeader);
            addHeaderCell(table, "Fecha recibido", fontHeader);
            addHeaderCell(table, "Fecha límite entrega", fontHeader);
            addHeaderCell(table, "Fecha entrega", fontHeader);
            addHeaderCell(table, "Estado", fontHeader);
            addHeaderCell(table, "Observación", fontHeader);
            addHeaderCell(table, "cantidad", fontHeader);
            addHeaderCell(table, "Precio unitario", fontHeader);
            addHeaderCell(table, "Folio compra", fontHeader);
            addHeaderCell(table, "Id Cliente", fontHeader);
            addHeaderCell(table, "Id Promoción", fontHeader);

            for (Pedido pedido : pedidos) {
                addCell(table, String.valueOf(pedido.getIdpedido()));
                addCell(table, String.valueOf(pedido.getFecharecibido()));
                addCell(table, String.valueOf(pedido.getFechalimiteentrega()));
                String fechaEntrega = pedido.getFechaentrega() != null ? String.valueOf(pedido.getFechaentrega()) : "Fecha no definida";
                addCell(table, String.valueOf(fechaEntrega));
                addCell(table, pedido.getEstado());
                addCell(table, pedido.getObservacion());
                addCell(table, String.valueOf(pedido.getCantidad()));
                addCell(table, String.valueOf(pedido.getPreciounitario()));
                addCell(table, pedido.getFoliocompra());
                addCell(table, String.valueOf(pedido.getCliente().getIdcliente()));
                String promocionDescripcion = pedido.getPromocion() != null ? String.format("ID %d", pedido.getPromocion().getIdpromocion()) : "Sin promoción";
                addCell(table, String.valueOf(promocionDescripcion));
            }

            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return outputStream;
	}
    
    
    
    
    
    
    public ByteArrayOutputStream generarReportePDFCliente(List<Cliente> clientes) {
    	Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            Font fontTitle = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLUE);
            Paragraph title = new Paragraph("Lista de Clientes", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9);
            PdfPTable table = new PdfPTable(5);

            addHeaderCell(table, "ID del cliente", fontHeader);
            addHeaderCell(table, "Nombre del cliente", fontHeader);
            addHeaderCell(table, "Dirección", fontHeader);
            addHeaderCell(table, "Teléfono", fontHeader);
            addHeaderCell(table, "Correo", fontHeader);

            for (Cliente cliente : clientes) {
                addCell(table, String.valueOf(cliente.getIdcliente()));
                addCell(table, cliente.getNombre());
                addCell(table, cliente.getDireccion());
                addCell(table, String.valueOf(cliente.getTelefono()));
                addCell(table, cliente.getCorreo());
            }

            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return outputStream;
	}
    
    
    
    
    public ByteArrayOutputStream generarReportePDFPromocion(List<Promocion> promociones) {
    	Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            Font fontTitle = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLUE);
            Paragraph title = new Paragraph("Lista de Promociones", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9);
            PdfPTable table = new PdfPTable(4);

            addHeaderCell(table, "ID de promoción", fontHeader);
            addHeaderCell(table, "Código descuento", fontHeader);
            addHeaderCell(table, "Fecha validez", fontHeader);
            addHeaderCell(table, "Detalles de uso", fontHeader);

            for (Promocion promocion : promociones) {
                addCell(table, String.valueOf(promocion.getIdpromocion()));
                addCell(table, String.valueOf(promocion.getCodigosdescuento()));
                addCell(table, String.valueOf(promocion.getFechavalidez()));
                addCell(table, promocion.getDetallesdeuso());
            }

            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return outputStream;
	}
    
    
    private void addHeaderCell(PdfPTable table, String text, Font font) {
        PdfPCell header = new PdfPCell();
        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        header.setBorderWidth(1);
        header.setPhrase(new Phrase(text, font));
        table.addCell(header);
    }

    private void addCell(PdfPTable table, String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setPadding(1);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);
    }

	
}
