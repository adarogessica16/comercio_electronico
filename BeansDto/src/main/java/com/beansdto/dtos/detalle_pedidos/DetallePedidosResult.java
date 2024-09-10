package com.beansdto.dtos.detalle_pedidos;

<<<<<<< HEAD
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
=======
public class DetallePedidosResult {
>>>>>>> 34b4adb00779896bc30d3e229191ddfc5bbc06b0
}
