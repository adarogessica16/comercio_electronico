package com.beansdto.domain.pedidos;

import com.beansdto.domain.base.BaseDomain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "pedidos")
public class PedidoDomain implements BaseDomain {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id_pedido", nullable = false, unique = true)
    private Integer iId_Pedido;

    @Column(name = "Id_usuario")
    private Integer Id_Usuario;

    @Column(name = "fecha")
    private String fecha;

    @Column(name = "estado")
    private boolean estado;

    @Column(name = "direccion")
    private String direccion;
}