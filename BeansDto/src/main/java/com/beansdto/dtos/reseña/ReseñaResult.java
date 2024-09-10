package com.beansdto.dtos.reseña;

import com.beansdto.dtos.base.BaseResult;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "reseñaResul")
public class ReseñaResult  extends BaseResult<ReseñaDTO> {
    private static final long serialVersionUID = 1L;

    @XmlElement
    public List<ReseñaDTO> getResenas(){return getList();}

    public void setPedidos(List<ReseñaDTO> resenhas){super.setList(resenhas);}
}
