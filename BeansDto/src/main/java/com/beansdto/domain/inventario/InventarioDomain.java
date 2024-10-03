package com.beansdto.domain.inventario;

import com.beansdto.domain.InventarioDetalles.InventarioDetallesDomain;
import com.beansdto.domain.base.BaseDomain;
import com.beansdto.domain.usuario.UsuarioDomain;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "inventarios")
public class InventarioDomain implements BaseDomain {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idInventario", nullable = false, unique = true)
    private Integer idInventario;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private UsuarioDomain usuario;

    @Column(name = "fecha", nullable = false)
    private Date fecha;

    // Relación uno a muchos con el detalle del inventario
    @OneToMany(mappedBy = "inventario", cascade = CascadeType.ALL)
    private List<InventarioDetallesDomain> detalles;
}

