package com.api_productos.dao.categoria;

import com.beansdto.domain.categoria.CategoriaDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICategoria extends JpaRepository<CategoriaDomain, Integer> {

    List<CategoriaDomain> findByNombreContainingIgnoreCase(String nombre);

}


