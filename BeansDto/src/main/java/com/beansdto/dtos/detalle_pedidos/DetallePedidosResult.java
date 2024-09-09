package com.beansdto.dtos.detalle_pedidos;

import com.beansdto.dtos.base.BaseResult;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;
@XmlRootElement(name = "detallePedidosResult")
public class DetallePedidosResult extends BaseResult<DetallePedidosDTO> {
    private static final long serialVersionUID = 1L;

    @XmlElement
    public List<DetallePedidosDTO> getDetallePedidos() {return getList();}

    public void setDetallePedidos(List<DetallePedidosDTO> detallePedidos) {super.setList(getDetallePedidos());}
}
