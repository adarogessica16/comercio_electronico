package com.beansdto.dtos.inventario;

import com.beansdto.dtos.base.BaseDTO;
import com.beansdto.dtos.inventarioDetalles.InventarioDetallesDTO;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;

@XmlRootElement(name = "inventario")
@Data
public class InventarioDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;
    private Integer idUsuario;
    private Date fecha;
    private List<InventarioDetallesDTO> detalles;
}

