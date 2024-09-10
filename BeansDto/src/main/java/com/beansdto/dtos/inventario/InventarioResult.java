package com.beansdto.dtos.inventario;

<<<<<<< HEAD
public class InventarioResult {
=======
import com.beansdto.dtos.base.BaseResult;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class InventarioResult extends BaseResult<InverntarioDTO> {
    private static final long serialVersionUID = 1L;
    @XmlElement
    public List<InverntarioDTO> getInventarios() {
        return getList();
    }
    public void setInventarios(List<InverntarioDTO> dtos) {
        super.setList(dtos);
    }
>>>>>>> 34b4adb00779896bc30d3e229191ddfc5bbc06b0
}
