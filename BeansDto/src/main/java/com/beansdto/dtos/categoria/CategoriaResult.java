package com.beansdto.dtos.categoria;


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
}
