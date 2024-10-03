package com.beansdto.dtos.rol;

import com.beansdto.dtos.base.BaseDTO;
import com.beansdto.dtos.usuario.UsuarioDTO;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Set;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Excluyo los campos null
@XmlRootElement(name = "rol")
public class RolDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private String descripcion;

    // Un rol puede estar asociado a muchos usuarios
    private Set<UsuarioDTO> usuarios;

    @Override
    public String toString() {
        return "RolDTO [id=" + getId() + ", nombre=" + nombre + ", descripcion=" + descripcion + ", usuarios=" + usuarios + "]";
    }
}
