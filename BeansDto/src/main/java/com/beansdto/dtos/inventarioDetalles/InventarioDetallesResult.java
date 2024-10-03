package com.beansdto.dtos.inventarioDetalles;

import com.beansdto.dtos.base.BaseResult;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class InventarioDetallesResult extends BaseResult<InventarioDetallesDTO> {
    private static final long serialVersionUID = 1L;

    @XmlElement(name = "inventarioDetalles")
    public List<InventarioDetallesDTO> getInventarioDetalles() {
        return getList();
    }

    public void setInventarioDetalles(List<InventarioDetallesDTO> dtos) {
        super.setList(dtos);
    }
}