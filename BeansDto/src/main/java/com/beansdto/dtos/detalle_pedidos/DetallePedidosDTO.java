package com.beansdto.dtos.detalle_pedidos;

import com.beansdto.dtos.base.BaseDTO;
import lombok.Data;

@Data
public class DetallePedidosDTO extends BaseDTO {
    private Integer Id_Detalle;
    private Integer Id_Producto;
    private Integer Id_Pedido;
    private int cantidad;
}
