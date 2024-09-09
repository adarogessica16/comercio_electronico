package com.beansdto.domain.producto;

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
@Table(name = "productos")

public class ProductoDomain implements BaseDomain {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id_producto", nullable = false, unique = true)
    private Integer Id_Producto;

    @Column(name = "Id_categoria")
    private Integer Id_Categoria;

    @Column(name = "Id_usuario")
    private Integer id_Usuario;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precio")
    private double precio;

}

