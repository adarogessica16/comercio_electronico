package com.api_productos.services.producto;

import com.api_productos.dao.categoria.ICategoria;
import com.api_productos.dao.producto.IProducto;
import com.api_productos.dao.usuarios.IUsuario;
import com.api_productos.services.categoria.CategoriaServiceImpl;
import com.beansdto.domain.categoria.CategoriaDomain;
import com.beansdto.domain.producto.ProductoDomain;
import com.beansdto.domain.usuario.UsuarioDomain;
import com.beansdto.dtos.producto.ProductoDTO;
import com.beansdto.dtos.producto.ProductoResult;
import com.api_productos.services.base.BaseServiceImpl;
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
public class ProductoServiceImpl extends BaseServiceImpl<ProductoDTO, ProductoDomain, ProductoResult> implements IProductoService {

    private static final Logger logger = LoggerFactory.getLogger(ProductoServiceImpl.class);

    @Autowired
    private IProducto productoDao;

    @Autowired
    private ICategoria categoriaDao;

    @Autowired
    private IUsuario usuarioDao;


    @Autowired
    private CategoriaServiceImpl categoriaService;

    @Override
    @Transactional
    @CachePut(value = "productos", key = "'api_producto_' + #result.id")
    public ProductoDTO save(ProductoDTO dto) {
        logger.info("Guardando un nuevo producto: {}", dto.getNombre());

        // Verificar si la categoría existe
        if (!categoriaExists(dto.getIdCategoria())) {
            logger.warn("La categoría con ID: {} no existe", dto.getIdCategoria());
            return null;
        }

        if (usuarioDao.findById(dto.getIdUsuario()).isEmpty()) {
            logger.warn("El usuario con ID: {} no existe", dto.getIdUsuario());
            return null;
        }

        ProductoDomain productoDomain = convertDtoToDomain(dto);
        ProductoDomain savedProducto = productoDao.save(productoDomain);
        logger.info("Producto guardado con éxito: {}", savedProducto.getIdProducto());

        // Actualizar la caché con el producto recién guardado usando el ID generado
        return convertDomainToDto(savedProducto);
    }



    @Override
    @CacheEvict(value = "productos", key = "'api_producto_' + #id")
    public void deleteById(Integer id) {
        logger.info("Eliminando producto con ID: {}", id);

        if (productoDao.findById(id).isPresent()) {
            productoDao.deleteById(id);
            logger.info("Producto con ID: {} eliminado con éxito", id);
        } else {
            logger.warn("El producto con ID: {} no existe", id);
        }
    }


    @Override
    @Transactional
    @Cacheable(value = "productos", key = "'api_producto_' + #id")
    public ProductoDTO getById(Integer id) {
        logger.info("Obteniendo producto con ID: {}", id);
        ProductoDTO productoDTO = productoDao.findById(id)
                .map(this::convertDomainToDto)
                .orElse(null);
        return productoDTO;
    }

    @Override
    public ProductoResult getAll(Pageable pageable) {
        logger.info("Obteniendo todos los productos paginados");
        Page<ProductoDomain> page = productoDao.findAll(pageable);
        List<ProductoDTO> productos = page.getContent()
                .stream()
                .map(this::convertDomainToDto)
                .collect(Collectors.toList());
        ProductoResult result = new ProductoResult();
        result.setProductos(productos);
        return result;
    }

    @Override
    public List<ProductoDTO> findByNombre(String nombre) {
        logger.info("Buscando productos que contengan el nombre: {}", nombre);
        List<ProductoDomain> productos = productoDao.findByNombreContainingIgnoreCase(nombre);
        return productos.stream()
                .map(this::convertDomainToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CachePut(value = "productos", key = "'api_producto_' + #id")
    public ProductoDTO updateById(ProductoDTO dto, Integer id) {
        if (dto == null) {
            throw new IllegalArgumentException("El ProductoDTO no puede ser nulo");
        }

        Optional<ProductoDomain> productoOpcional = productoDao.findById(id);
        if (productoOpcional.isEmpty()) {
            throw new RuntimeException("Producto no encontrado con ID: " + id);
        }

        ProductoDomain productoExistente = productoOpcional.get();
        if (!categoriaExists(dto.getIdCategoria())) {
            throw new RuntimeException("La categoría con ID: " + dto.getIdCategoria() + " no existe");
        }
        if (usuarioDao.findById(dto.getIdUsuario()).isEmpty()) {
            throw new RuntimeException("El usuario con ID: " + dto.getIdUsuario() + " no existe");
        }
        productoExistente.setNombre(dto.getNombre());
        productoExistente.setDescripcion(dto.getDescripcion());
        productoExistente.setPrecio(dto.getPrecio());
        if (!productoExistente.getCategoria().getIdCategoria().equals(dto.getIdCategoria())) {
            CategoriaDomain nuevaCategoria = obtenerCategoria(dto.getIdCategoria());
            productoExistente.setCategoria(nuevaCategoria);
        }

        if (!productoExistente.getUsuario().getId_Usuario().equals(dto.getIdUsuario())) {
            UsuarioDomain nuevoUsuario = obtenerUsuario(dto.getIdUsuario());
            productoExistente.setUsuario(nuevoUsuario);
        }

        ProductoDomain productoActualizado = productoDao.save(productoExistente);
        logger.info("Producto actualizado con éxito: {}", productoActualizado.getIdProducto());
        return convertDomainToDto(productoActualizado);
    }

    @Override
    @Transactional
    public List<ProductoDTO> findByCategoriaId(Integer categoriaId) {
        logger.info("Obteniendo productos de la categoría con ID: {}", categoriaId);
        List<ProductoDomain> productos = productoDao.findByCategoriaIdCategoria(categoriaId);
        return productos.stream()
                .map(this::convertDomainToDto)
                .collect(Collectors.toList());
    }

    @Override
    protected ProductoDTO convertDomainToDto(ProductoDomain domain) {
        if (domain == null) {
            return null;
        }
        ProductoDTO dto = new ProductoDTO();
        dto.setId(domain.getIdProducto());
        dto.setIdCategoria(domain.getCategoria().getIdCategoria());
        dto.setIdUsuario(domain.getUsuario().getId_Usuario());
        dto.setNombre(domain.getNombre());
        dto.setDescripcion(domain.getDescripcion());
        dto.setPrecio(domain.getPrecio());
        return dto;
    }

    @Override
    protected ProductoDomain convertDtoToDomain(ProductoDTO dto) {
        if (dto == null) {
            return null;
        }
        ProductoDomain domain = new ProductoDomain();
        domain.setIdProducto(dto.getId());
        UsuarioDomain usuarioDomain=obtenerUsuario(dto.getIdUsuario());
        domain.setUsuario(usuarioDomain);
        domain.setNombre(dto.getNombre());
        domain.setDescripcion(dto.getDescripcion());
        domain.setPrecio(dto.getPrecio());
        CategoriaDomain categoriaDomain = obtenerCategoria(dto.getIdCategoria());
        domain.setCategoria(categoriaDomain);
        return domain;
    }

    // Método auxiliar para verificar si la categoría existe
    private boolean categoriaExists(Integer categoriaId) {
        return categoriaService.getById(categoriaId) != null;
    }

    private CategoriaDomain obtenerCategoria(Integer id){
        Optional<CategoriaDomain> categoriaOpt = categoriaDao.findById(id);
        return categoriaOpt.orElseThrow(() -> new RuntimeException("Categoria no encontrado con ID: " + id));

    }

    public UsuarioDomain obtenerUsuario(Integer id){
        Optional<UsuarioDomain> usuario = usuarioDao.findById(id);
        return usuario.orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

    }
}





