package com.beansdto.dtos.producto;

<<<<<<< HEAD
public class ProductoResult {
=======
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
>>>>>>> 34b4adb00779896bc30d3e229191ddfc5bbc06b0
}
