package com.beansdto.dtos.usuario;

<<<<<<< HEAD
public class UsuarioDTO {
=======
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
>>>>>>> 34b4adb00779896bc30d3e229191ddfc5bbc06b0
}
