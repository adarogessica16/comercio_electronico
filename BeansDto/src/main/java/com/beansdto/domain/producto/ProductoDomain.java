package com.beansdto.domain.producto;

import com.beansdto.domain.base.BaseDomain;
import com.beansdto.domain.categoria.CategoriaDomain;
import com.beansdto.domain.usuario.UsuarioDomain;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "productos")
public class ProductoDomain implements BaseDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idProducto", nullable = false, unique = true)
    private Integer idProducto;

    @ManyToOne
    @JoinColumn(name = "idCategoria", nullable = false)
    private CategoriaDomain categoria;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private UsuarioDomain usuario;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precio")
    private double precio;
}
