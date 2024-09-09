package com.beansdto.domain.detalle_pedidos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import com.beansdto.domain.base.BaseDomain;

@Data
@Entity
@Table(name = "detallesPedidos")

public class DetallesPedidosDomain implements BaseDomain {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id_Detalle", nullable = false, unique = true)
    private Integer Id_Detalle;

    @Column(name = "Id_producto")
    private Integer Id_Producto;

    @Column(name = "Id_pedido")
    private Integer Id_Pedido;

    @Column(name = "cantidad")
    private int cantidad;

}
