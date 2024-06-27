package com.ipn.mx.modelo.entidades;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpedido")
    private Long idpedido;

	@Temporal(TemporalType.DATE)
    @Column(name = "fecharecibido", nullable = false)
    private Date fecharecibido;

	@Temporal(TemporalType.DATE)
    @Column(name = "fechalimiteentrega", nullable = false)
    private Date fechalimiteentrega;

	@Temporal(TemporalType.DATE)
    @Column(name = "fechaentrega")
    private Date fechaentrega;

    @Column(name = "estado", nullable = false, length = 50)
    private String estado;
    
    @Column(name = "observacion", nullable = false, length = 200)
    private String observacion;

    @Column(name = "cantidad", nullable = false)
    private Float cantidad;

    @Column(name = "preciounitario", nullable = false)
    private Float preciounitario;
    
    @Column(name = "foliocompra", nullable = false)
    private String foliocompra;

    @ManyToOne
    @JoinColumn(name = "idcliente", referencedColumnName = "idcliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "idpromocion",  referencedColumnName = "idpromocion")
    private Promocion promocion;
    
    @OneToOne
    @JoinColumn(name = "idhistorial",  referencedColumnName = "idhistorial")
    private Historial historial;
 
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
    		name = "Pedido_has_producto",
    		joinColumns = @JoinColumn(name = "idpedido", referencedColumnName = "idpedido"),
    		inverseJoinColumns = @JoinColumn(name = "idproducto", referencedColumnName = "idproducto")
    )
    private List<Producto> productos;
}