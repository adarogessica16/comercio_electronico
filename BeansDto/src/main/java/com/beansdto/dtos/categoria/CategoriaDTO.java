package com.beansdto.dtos.categoria;

import com.beansdto.dtos.base.BaseDTO;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@XmlRootElement(name = "categoria")
@Data
public class CategoriaDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;
    private String nombre;
    private String descripcion;
}
