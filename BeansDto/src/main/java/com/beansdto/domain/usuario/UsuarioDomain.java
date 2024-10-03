package com.beansdto.domain.usuario;

import jakarta.persistence.*;
import lombok.Data;
import com.beansdto.domain.base.BaseDomain;
import com.beansdto.domain.rol.RolDomain;

import java.util.Set;

@Data
@Entity
@Table(name = "usuarios")
public class UsuarioDomain implements BaseDomain {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id_Usuario", nullable = false, unique = true)
    private Integer Id_Usuario;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "contraseña")
    private String contraseña;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "direccion")
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "Id_Rol")
    private RolDomain rol;
}
