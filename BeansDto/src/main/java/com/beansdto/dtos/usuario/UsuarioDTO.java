package com.beansdto.dtos.usuario;

import jakarta.xml.bind.annotation.XmlRootElement;
import com.beansdto.dtos.base.BaseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlRootElement(name = "usuario")
public class UsuarioDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    private String nombre;
    private String email;
    private String contraseña;
    private String telefono;
    private String direccion;
    private Integer idRol;

    @Override
    public String toString() {
        return "UsuarioDTO [id=" + getId() + ", nombre=" + nombre + ", email=" + email +
                ", telefono=" + telefono + ", direccion=" + direccion + ", idRol=" + idRol + "]";
    }
}
