package com.api_productos.dao.inventario;

import com.beansdto.domain.inventario.InventarioDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IInventario extends JpaRepository<InventarioDomain, Integer> {
}
