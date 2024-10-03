package com.api_productos.dao.usuarios;

import com.beansdto.domain.usuario.UsuarioDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuario extends JpaRepository<UsuarioDomain, Integer> {
}
