package com.beansdto.dtos.categoria;

<<<<<<< HEAD
public class CategoriaResult {
=======
import com.beansdto.dtos.base.BaseResult;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;

public class CategoriaResult extends BaseResult<CategoriaDTO> {
    private static final long serialVersionUID = 1L;
    @XmlElement
    public List<CategoriaDTO> getCategorias() {
        return getList();
    }
    public void setCategorias(List<CategoriaDTO> dtos) {
        super.setList(dtos);
    }
>>>>>>> 34b4adb00779896bc30d3e229191ddfc5bbc06b0
}
