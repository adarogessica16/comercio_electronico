package com.api_productos.controller.producto;

import com.api_productos.services.producto.IProductoService;
import com.beansdto.dtos.producto.ProductoDTO;
import com.beansdto.dtos.producto.ProductoResult;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoResource {

    private static final Logger logger = LoggerFactory.getLogger(ProductoResource.class);

    @Autowired
    private IProductoService productoService;

    @Value("${numPag}")
    private int numPag;

    @Operation(summary = "Obtener todos los productos")
    @GetMapping(path = "/page/{page_num}")
    public ResponseEntity<ProductoResult> obtenerTodos(@PathVariable(value = "page_num") Integer pageNum) {
        logger.info("Recibiendo solicitud para obtener productos en la página: {}", pageNum);
        Pageable pageable = PageRequest.of(pageNum-1, numPag);
        ProductoResult result = productoService.getAll(pageable);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Obtener un producto por ID")
    @GetMapping("/{id}")
    @Cacheable(value = "productos", key = "#id")
    public ProductoDTO obtenerPorId(@PathVariable Integer id) {
        logger.info("Recibiendo solicitud para obtener producto con ID: {}", id);
        ProductoDTO producto = productoService.getById(id);
        if (producto != null) {
            logger.info("Producto encontrado: {}", producto.getNombre());
            return producto;
        } else {
            logger.warn("Producto con ID: {} no encontrado", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado");
        }
    }


    @Operation(summary = "Crear un nuevo producto")
    @PostMapping
    public ResponseEntity<ProductoDTO> crearProducto(@RequestBody ProductoDTO productoDTO) {
        logger.info("Recibiendo solicitud para crear un nuevo producto: {}", productoDTO.getNombre());
        ProductoDTO nuevoProducto = productoService.save(productoDTO);
        return ResponseEntity.ok(nuevoProducto);
    }

    @Operation(summary = "Actualizar un producto por ID")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ProductoDTO> actualizarProducto(@PathVariable Integer id, @RequestBody ProductoDTO productoDTO) {
        logger.info("Recibiendo solicitud para actualizar producto con ID: {}", id);
        ProductoDTO productoActualizado = productoService.updateById(productoDTO, id);
        if (productoActualizado != null) {
            logger.info("Producto actualizado con éxito: {}", productoActualizado.getNombre());
            return ResponseEntity.ok(productoActualizado);
        } else {
            logger.warn("No se pudo actualizar el producto con ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar un producto por ID")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Integer id) {
        logger.info("Recibiendo solicitud para eliminar producto con ID: {}", id);
        productoService.deleteById(id);
        logger.info("Producto con ID: {} eliminado con éxito", id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar productos por nombre")
    @GetMapping("/buscar")
    public List<ProductoDTO> searchByNombre(@RequestParam String nombre) {
        logger.info("Recibiendo solicitud para buscar productos con nombre que contenga: {}", nombre);
        return productoService.findByNombre(nombre);
    }

    @Operation(summary = "Obtener productos de una categoría por ID")
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<ProductoDTO>> obtenerProductosPorCategoria(@PathVariable Integer categoriaId) {
        logger.info("Recibiendo solicitud para obtener productos de la categoría: {}", categoriaId);
        List<ProductoDTO> productos = productoService.findByCategoriaId(categoriaId);
        return ResponseEntity.ok(productos);
    }

}

