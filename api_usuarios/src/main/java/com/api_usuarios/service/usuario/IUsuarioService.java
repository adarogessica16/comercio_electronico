package com.api_usuarios.service.usuario;

import com.beansdto.dtos.usuario.UsuarioDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUsuarioService {

    List<UsuarioDTO> findAll();

    Page<UsuarioDTO> findAllPaginated(int page, int size);

    UsuarioDTO findById(Integer id);

    List<UsuarioDTO> findByNombre(String nombre);

    UsuarioDTO create(UsuarioDTO usuarioDTO);

    UsuarioDTO update(Integer id, UsuarioDTO usuarioDTO);

    ResponseEntity<String> delete(Integer id);
}
