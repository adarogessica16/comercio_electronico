package com.beansdto.dtos.pedidos;

import com.beansdto.dtos.base.BaseDTO;
import lombok.Data;

@Data
public class PedidoDTO extends BaseDTO {
    private Integer Id_Pedido;
    private Integer Id_Usuario;
    private String fecha;
    private boolean estado;
    private String direccion;
}


