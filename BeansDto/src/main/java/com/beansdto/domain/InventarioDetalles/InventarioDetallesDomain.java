package com.beansdto.domain.InventarioDetalles;
import com.beansdto.domain.base.BaseDomain;
import com.beansdto.domain.inventario.InventarioDomain;
import com.beansdto.domain.producto.ProductoDomain;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "inventario_detalles")
public class InventarioDetallesDomain implements BaseDomain {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idDetalle", nullable = false, unique = true)
    private Integer idDetalle;

    @ManyToOne
    @JoinColumn(name = "idInventario", nullable = false)
    private InventarioDomain inventario;

    @ManyToOne
    @JoinColumn(name = "idProducto", nullable = false)
    private ProductoDomain producto;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;
}
