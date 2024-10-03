package com.api_productos.dao.InventarioDetalles;

import com.beansdto.domain.InventarioDetalles.InventarioDetallesDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IInventarioDetalles extends JpaRepository<InventarioDetallesDomain, Integer> {

}
