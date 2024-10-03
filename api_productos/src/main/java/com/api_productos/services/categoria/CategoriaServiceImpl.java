package com.api_productos.services.categoria;

import com.api_productos.dao.categoria.ICategoria;
import com.api_productos.services.producto.ProductoServiceImpl;
import com.beansdto.domain.categoria.CategoriaDomain;
import com.beansdto.dtos.categoria.CategoriaDTO;
import com.beansdto.dtos.categoria.CategoriaResult;
import com.api_productos.services.base.BaseServiceImpl;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaServiceImpl extends BaseServiceImpl<CategoriaDTO, CategoriaDomain, CategoriaResult> implements ICategoriaService {

    private static final Logger logger = LoggerFactory.getLogger(CategoriaServiceImpl.class);
    @Autowired
    private ICategoria categoriaDao;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    @Override
    @CachePut(value = "categorias", key = "'api_producto_' + #result.id")
    public CategoriaDTO save(CategoriaDTO dto) {
        logger.info("Guardando una nueva categoria: {}", dto.getNombre());
        CategoriaDomain categoriaDomain = convertDtoToDomain(dto);
        CategoriaDomain savedCategoria = categoriaDao.save(categoriaDomain);
        return convertDomainToDto(savedCategoria);
    }

    @Override
    @CacheEvict(value = "categorias", key = "'api_producto_' + #id")
    public void deleteById(Integer id) {
        logger.info("Eliminando categoria con ID: {}", id);
        categoriaDao.deleteById(id);
    }

    @Transactional
    @Override
    @Cacheable(value = "categorias", key = "'api_categoria_' + #id")
    public CategoriaDTO getById(Integer id) {
        return categoriaDao.findById(id)
                .map(this::convertDomainToDto)
                .orElse(null);
    }

    @Override
    @Transactional
    @Cacheable(value = "categorias", key = "'api_producto_' + #nombre")
    public List<CategoriaDTO> getByNombre(String nombre) {
        List<CategoriaDomain> categorias = categoriaDao.findByNombreContainingIgnoreCase(nombre);
        return categorias.stream()
                .map(this::convertDomainToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CategoriaResult getAll(Pageable pageable) {
        Page<CategoriaDomain> page = categoriaDao.findAll(pageable);
        List<CategoriaDTO> categorias = page.getContent()
                .stream()
                .map(this::convertDomainToDto)
                .collect(Collectors.toList());
        CategoriaResult result = new CategoriaResult();
        result.setCategorias(categorias);

        return result;
    }

    @Override
    @Transactional
    @CachePut(value = "categorias", key = "'api_producto_' + #id")
    public CategoriaDTO updateById(CategoriaDTO dto, Integer id) {
        if (dto == null) {
            throw new IllegalArgumentException("El CategoriaDTO no puede ser nulo");
        }

        Optional<CategoriaDomain> categoriaOpcional = categoriaDao.findById(id);
        if (categoriaOpcional.isEmpty()) {
            throw new RuntimeException("Categoría no encontrada con ID: " + id);
        }

        CategoriaDomain categoriaExistente = categoriaOpcional.get();

        // Actualizar campos
        categoriaExistente.setNombre(dto.getNombre());
        categoriaExistente.setDescripcion(dto.getDescripcion());

        CategoriaDomain categoriaActualizada = categoriaDao.save(categoriaExistente);
        return convertDomainToDto(categoriaActualizada);
    }


    @Override
    protected CategoriaDTO convertDomainToDto(CategoriaDomain domain) {
        return modelMapper.map(domain, CategoriaDTO.class);
    }

    @Override
    protected CategoriaDomain convertDtoToDomain(CategoriaDTO dto) {
        return modelMapper.map(dto, CategoriaDomain.class);
    }
}





