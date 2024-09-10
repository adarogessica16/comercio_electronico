package com.beansdto.dtos.rol;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import com.beansdto.dtos.base.BaseResult;

@XmlRootElement(name = "rolResult")
public class RolResult extends BaseResult<RolDTO> {

    private static final long serialVersionUID = 1L;

    /**
     * Obtiene la lista de roles desde el método 'getList()' heredado de BaseResult.
     * @return Lista de RolDTO.
     */
    @XmlElement
    public List<RolDTO> getRoles() {
        return getList();
    }

    /**
     * Establece la lista de roles usando el método 'setList()' heredado de BaseResult.
     * @param roles Lista de RolDTO a establecer.
     */
    public void setRoles(List<RolDTO> roles) {
        super.setList(roles);
    }
}
