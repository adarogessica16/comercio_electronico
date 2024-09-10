package com.beansdto.dtos.usuario;

<<<<<<< HEAD
public class UsuarioResult {
=======
import java.util.List;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import com.beansdto.dtos.base.BaseResult;

@XmlRootElement(name = "usuarioResult")
public class UsuarioResult extends BaseResult<UsuarioDTO> {

    private static final long serialVersionUID = 1L;

    /**
     * Obtiene la lista de usuarios desde el método 'getList()' heredado de BaseResult.
     * @return Lista de UsuarioDTO.
     */
    @XmlElement
    public List<UsuarioDTO> getUsuarios() {
        return getList();
    }

    /**
     * Establece la lista de usuarios usando el método 'setList()' heredado de BaseResult.
     * @param usuarios Lista de UsuarioDTO a establecer.
     */
    public void setUsuarios(List<UsuarioDTO> usuarios) {
        super.setList(usuarios);
    }
>>>>>>> 34b4adb00779896bc30d3e229191ddfc5bbc06b0
}
