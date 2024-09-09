package com.beansdto.domain.reseña;

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
@Table(name = ("reseñas"))
public class ReseñaDomain implements BaseDomain {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id_reseña", nullable = false, unique = true)
    private Integer Id_Reseña;

    @Column(name = "Id_usuario")
    private Integer Id_Usuario;

    @Column(name = "Id_producto")
    private Integer Id_Producto;

    @Column(name = "calificacion")
    private int calificacion;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "fecha")
    private String fecha;
}
