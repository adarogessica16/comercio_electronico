package com.beansdto.dtos.pedidos;

<<<<<<< HEAD
import com.beansdto.dtos.base.BaseResult;

import java.util.List;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pedidosResult")
public class PedidoResult extends BaseResult<PedidoDTO>{

    private static final long serialVersionUID = 1L;

    @XmlElement
    public List<PedidoDTO> getPedidos(){return getList();}

    public void setPedidos(List<PedidoDTO> pedidos){super.setList(pedidos);}

=======
public class PedidoResult {
>>>>>>> 34b4adb00779896bc30d3e229191ddfc5bbc06b0
}
