package com.api_usuarios.dao.rol;

import com.beansdto.domain.rol.RolDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolDao extends JpaRepository<RolDomain, Integer> {
    Optional<RolDomain> findByNombre(String nombre);
}
