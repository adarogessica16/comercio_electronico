package com.beansdto.dtos.inventarioDetalles;

import com.beansdto.dtos.base.BaseDTO;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "inventarioDetalle")
@Data
public class InventarioDetallesDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;
    private Integer idInventario;
    private Integer idProducto;
    private int cantidad;
}

