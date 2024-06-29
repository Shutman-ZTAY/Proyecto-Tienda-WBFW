package com.ipn.mx.modelo.entidades;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Envio")
public class Envio implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idEnvio", nullable = false)
	private Long idEnvio;
	@Column(name = "nombreRepartidor", length = 200, nullable = false)
	private String nombreRepartidor;
	@Column(name = "correo", length = 100, nullable = false)
	private String correo;
	@Column(name = "empresaProcediente", length = 80, nullable = false)
	private String empresaProcediente;
	@Column(name = "costo", nullable = false)
	private Float costo;
	@ManyToOne
    @JoinColumn(name = "idPedido", nullable = false)
	private Pedido pedido;
}
