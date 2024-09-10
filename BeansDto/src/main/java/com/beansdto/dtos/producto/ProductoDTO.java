package com.beansdto.dtos.producto;

import com.beansdto.dtos.base.BaseDTO;
import lombok.Data;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "producto")
@Data
public class ProductoDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;
    private Integer Id_Categoria;
    private Integer Id_Usuario;
    private String nombre;
    private String descripcion;
    private double precio;
}

