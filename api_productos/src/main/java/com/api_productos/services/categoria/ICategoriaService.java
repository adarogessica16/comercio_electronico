package com.api_productos.services.categoria;

import com.api_productos.services.base.IBaseService;
import com.beansdto.dtos.categoria.CategoriaDTO;
import com.beansdto.dtos.categoria.CategoriaResult;
import com.beansdto.dtos.producto.ProductoDTO;

import java.util.List;

public interface ICategoriaService extends IBaseService<CategoriaDTO, CategoriaResult> {

    public List<CategoriaDTO> getByNombre(String nombre);

}

