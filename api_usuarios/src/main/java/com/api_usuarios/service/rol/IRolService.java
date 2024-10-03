package com.api_usuarios.service.rol;

import com.beansdto.dtos.rol.RolDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IRolService {

    List<RolDTO> findAll();

    Page<RolDTO> findAllPaginated(int page, int size);

    RolDTO findById(Integer id);

    RolDTO create(RolDTO rolDTO);

    RolDTO update(Integer id, RolDTO rolDTO);

    RolDTO patch(Integer id, RolDTO rolDTO);

    void delete(Integer id);
}
