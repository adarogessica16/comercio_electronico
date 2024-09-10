package com.beansdto.dtos.inventario;

import com.beansdto.dtos.base.BaseDTO;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "inventario")
@Data
public class InverntarioDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;
    private Integer Id_Producto;
    private Integer Id_Usuario;
    private int cantidad;
}
