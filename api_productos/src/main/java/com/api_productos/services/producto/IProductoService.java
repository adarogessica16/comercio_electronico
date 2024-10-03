package com.api_productos.services.producto;

import com.api_productos.services.base.IBaseService;
import com.beansdto.dtos.producto.ProductoDTO;
import com.beansdto.dtos.producto.ProductoResult;

import java.util.List;

public interface IProductoService extends IBaseService<ProductoDTO, ProductoResult> {

    List<ProductoDTO> findByNombre(String nombre);
    List<ProductoDTO> findByCategoriaId(Integer categoriaId);

}
