package com.api_productos.controller.categoria;

import com.api_productos.services.categoria.ICategoriaService;
import com.beansdto.dtos.categoria.CategoriaDTO;
import com.beansdto.dtos.categoria.CategoriaResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@Tag(name = "Categorías", description = "API para la gestión de categorías")
public class CategoriaResource {

    @Autowired
    private ICategoriaService categoriaService;
    @Value("${numPag}")
    private int numPag;

    @Operation(summary = "Obtener todas las categorías", description = "Devuelve una lista de todas las categorías disponibles")
    @GetMapping(path = "/page/{page_num}")
    public ResponseEntity<CategoriaResult> obtenerTodas(@PathVariable(value = "page_num") Integer pageNum) {
        Pageable pageable = PageRequest.of(pageNum-1, numPag);
        CategoriaResult result = categoriaService.getAll(pageable);
        return ResponseEntity.ok(result);
    }


    @Operation(summary = "Obtener una categoría por ID", description = "Devuelve una categoría según su ID")
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> obtenerPorId(@PathVariable Integer id) {
        CategoriaDTO categoria = categoriaService.getById(id);
        if (categoria != null) {
            return ResponseEntity.ok(categoria);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Crear una nueva categoría", description = "Crea una nueva categoría con los datos proporcionados")
    @PostMapping
    public ResponseEntity<CategoriaDTO> crearCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        CategoriaDTO nuevaCategoria = categoriaService.save(categoriaDTO);
        return ResponseEntity.ok(nuevaCategoria);
    }

    @Operation(summary = "Actualizar una categoría por ID", description = "Actualiza una categoría específica por su ID")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<CategoriaDTO> actualizarCategoria(@PathVariable Integer id, @RequestBody CategoriaDTO categoriaDTO) {
        CategoriaDTO categoriaActualizada = categoriaService.updateById(categoriaDTO,id);
        if (categoriaActualizada != null) {
            return ResponseEntity.ok(categoriaActualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar una categoría por ID", description = "Elimina una categoría específica por su ID")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Integer id) {
        categoriaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener categorías por nombre", description = "Devuelve una lista de categorías que coincidan con el nombre proporcionado")
    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<List<CategoriaDTO>> obtenerPorNombre(@PathVariable String nombre) {
        List<CategoriaDTO> categorias = categoriaService.getByNombre(nombre);
        return ResponseEntity.ok(categorias);
    }
}

