package com.api_usuarios.dao.usuario;

import com.beansdto.domain.usuario.UsuarioDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioDao extends JpaRepository<UsuarioDomain, Integer> {

    List<UsuarioDomain> findByNombreContainingIgnoreCase(String nombre);

}
