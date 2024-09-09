package com.beansdto.domain.inventario;

import com.beansdto.domain.base.BaseDomain;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "inventarios")
public class InventarioDomain implements BaseDomain{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id_inventario", nullable = false, unique = true)
    private Integer Id_Inventario;

    @Column(name = "Id_producto")
    private Integer Id_Producto;

    @Column(name = "Id_usuarios")
    private Integer Id_Usuarios;

    @Column(name = "cantidad")
    private int cantidad;
}
