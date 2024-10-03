package com.api_productos.dao.producto;


import com.beansdto.domain.producto.ProductoDomain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProducto extends JpaRepository<ProductoDomain, Integer> {

    List<ProductoDomain> findByNombreContainingIgnoreCase(String nombre);
    List<ProductoDomain> findByCategoriaIdCategoria(Integer categoriaId);

}
