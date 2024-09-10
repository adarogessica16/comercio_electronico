package com.beansdto.dtos.inventario;

import com.beansdto.dtos.base.BaseResult;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class InventarioResult extends BaseResult<InventarioDTO> {
    private static final long serialVersionUID = 1L;
    @XmlElement
    public List<InventarioDTO> getInventarios() {
        return getList();
    }
    public void setInventarios(List<InventarioDTO> dtos) {
        super.setList(dtos);
    }
}
