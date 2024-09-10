package com.beansdto.dtos.rol;

import jakarta.xml.bind.annotation.XmlRootElement;
import com.beansdto.dtos.base.BaseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlRootElement(name = "rol")
public class RolDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    private String nombre;
    private String descripcion;

    @Override
    public String toString() {
        return "RolDTO [id=" + getId() + ", nombre=" + nombre + ", descripcion=" + descripcion + "]";
    }
}
