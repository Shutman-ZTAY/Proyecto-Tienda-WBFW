package com.ipn.mx.modelo.entidades;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "promocion")
public class Promocion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpromocion")
    private Long idpromocion;

    @Column(name = "codigosdescuento", nullable = false)
    private Integer codigosdescuento;

    @Temporal(TemporalType.DATE)
    @Column(name = "fechavalidez", nullable = false)
    private Date fechavalidez;

    @Column(name = "detallesdeuso", nullable = false, length = 100)
    private String detallesdeuso;
}
