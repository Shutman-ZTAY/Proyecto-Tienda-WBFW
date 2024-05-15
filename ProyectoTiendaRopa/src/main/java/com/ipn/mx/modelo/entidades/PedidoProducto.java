package com.ipn.mx.modelo.entidades;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//Entidad que permite manejar relaciones many to many en la logica de base de datos

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Pedido_has_Producto")
public class PedidoProducto implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManyToOne
    @JoinColumn(name = "idProducto", nullable = false)
	private Producto producto;
	@ManyToOne
    @JoinColumn(name = "idPedido", nullable = false)
	private Pedido pedido;
}
