package com.ipn.mx.modelo.dto;

import java.util.List;

import com.ipn.mx.modelo.entidades.Categoria;
import com.ipn.mx.modelo.entidades.Pedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductoDTO {

    private Long idproducto;
    private String nombre;
    private String descripcion;
    private Float precio;
    private String tamaño;
    private String color;
    private Integer disponibilidad;
    private Categoria categoria;
    private List<Pedido> pedidos;
}
