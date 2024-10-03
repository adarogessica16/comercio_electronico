package com.api_productos.controller.inventario;

import com.api_productos.services.inventario.IInventarioBase;
import com.beansdto.dtos.inventario.InventarioDTO;
import com.beansdto.dtos.inventario.InventarioResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventarios")
@Tag(name = "Inventarios", description = "API para la gestión de inventarios")
public class InventarioResource {

    @Autowired
    private IInventarioBase inventarioService;
    @Value("${numPag}")
    private int numPag;

    @Operation(summary = "Obtener todos los inventarios", description = "Devuelve una lista de todos los inventarios disponibles")
    @GetMapping(path = "/page/{page_num}")
    public ResponseEntity<InventarioResult> obtenerTodos(@PathVariable(value = "page_num") Integer pageNum) {
        Pageable pageable = PageRequest.of(pageNum-1, numPag);
        InventarioResult result = inventarioService.getAll(pageable);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Obtener un inventario por ID", description = "Devuelve un inventario según su ID")
    @GetMapping("/{id}")
    public ResponseEntity<InventarioDTO> obtenerPorId(@PathVariable Integer id) {
        InventarioDTO inventario = inventarioService.getById(id);
        if (inventario != null) {
            return ResponseEntity.ok(inventario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Crear un nuevo inventario", description = "Crea un nuevo inventario con los datos proporcionados")
    @PostMapping
    public ResponseEntity<InventarioDTO> crearInventario(@RequestBody InventarioDTO inventarioDTO) {
        InventarioDTO nuevoInventario = inventarioService.save(inventarioDTO);
        return ResponseEntity.ok(nuevoInventario);
    }

    @Operation(summary = "Actualizar un inventario por ID", description = "Actualiza un inventario específico por su ID")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<InventarioDTO> actualizarInventario(@PathVariable Integer id, @RequestBody InventarioDTO inventarioDTO) {
        InventarioDTO inventarioActualizado = inventarioService.updateById(inventarioDTO,id);
        if (inventarioActualizado != null) {
            return ResponseEntity.ok(inventarioActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar un inventario por ID", description = "Elimina un inventario específico por su ID")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarInventario(@PathVariable Integer id) {
        inventarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}

