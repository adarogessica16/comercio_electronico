package com.beansdto.domain.rol;

import jakarta.persistence.*;
import lombok.Data;
import com.beansdto.domain.base.BaseDomain;
import com.beansdto.domain.usuario.UsuarioDomain;

import java.util.Set;

@Data
@Entity
@Table(name = "roles")
public class RolDomain implements BaseDomain {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id_Rol", nullable = false, unique = true)
    private Integer Id_Rol;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(mappedBy = "rol")
    private Set<UsuarioDomain> usuarios;
}

