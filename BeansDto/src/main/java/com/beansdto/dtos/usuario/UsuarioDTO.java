package com.beansdto.dtos.usuario;

import com.beansdto.dtos.rol.RolDTO;
import com.beansdto.dtos.base.BaseDTO;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlRootElement(name = "usuario")
public class UsuarioDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private String apellido;
    private String username;
    private String email;
    private String contraseña;
    private String telefono;
    private String direccion;
    private RolDTO rol;

    @Override
    public String toString() {
        return "UsuarioDTO [id=" + getId() + ", nombre=" + nombre + ", apellido=" + apellido + "username="+username + email +
                ", telefono=" + telefono + ", direccion=" + direccion + ", rol=" + rol + "]";
    }
}
