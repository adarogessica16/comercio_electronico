package com.beansdto.dtos.categoria;

import com.beansdto.dtos.base.BaseDTO;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@XmlRootElement(name = "categoria")
@Data
public class CategoriaDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;
    private String nombre;
    private String descripcion;

    @Override
    public String toString() {
        return "CategoriaDTO [nombre=" + nombre + ", descripcion=" + descripcion+ "]";
    }
}
