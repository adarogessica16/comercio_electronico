package com.beansdto.dtos.producto;

import com.beansdto.dtos.base.BaseResult;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class ProductoResult extends BaseResult<ProductoDTO> {
    private static final long serialVersionUID = 1L;
    @XmlElement
    public List<ProductoDTO> getProductos() {
        return getList();
    }
    public void setProductos(List<ProductoDTO> dtos) {
        super.setList(dtos);
    }
}
