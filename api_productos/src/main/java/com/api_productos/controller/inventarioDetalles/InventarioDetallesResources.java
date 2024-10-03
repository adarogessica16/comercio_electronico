package com.api_productos.controller.inventarioDetalles;


import com.api_productos.services.InventarioDetalles.IInventarioDetallesBase;
import com.beansdto.dtos.inventarioDetalles.InventarioDetallesDTO;
import com.beansdto.dtos.inventarioDetalles.InventarioDetallesResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventario-detalles")
@Tag(name = "Inventario Detalles", description = "API para la gestión de detalles de inventario")
public class InventarioDetallesResources {

    @Autowired
    private IInventarioDetallesBase inventarioDetallesService;

    @Value("${numPag}")
    private int numPag;

    @Operation(summary = "Obtener todos los detalles de inventario", description = "Devuelve una lista paginada de todos los detalles de inventario disponibles")
    @GetMapping(path = "/page/{page_num}")
    public ResponseEntity<InventarioDetallesResult> obtenerTodos(@PathVariable(value = "page_num") Integer pageNum) {
        Pageable pageable = PageRequest.of(pageNum-1, numPag);
        InventarioDetallesResult result = inventarioDetallesService.getAll(pageable);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Obtener un detalle de inventario por ID", description = "Devuelve un detalle de inventario según su ID")
    @GetMapping("/{id}")
    public ResponseEntity<InventarioDetallesDTO> obtenerPorId(@PathVariable Integer id) {
        InventarioDetallesDTO detalle = inventarioDetallesService.getById(id);
        if (detalle != null) {
            return ResponseEntity.ok(detalle);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Crear un nuevo detalle de inventario", description = "Crea un nuevo detalle de inventario con los datos proporcionados")
    @PostMapping
    public ResponseEntity<InventarioDetallesDTO> crearInventarioDetalle(@RequestBody InventarioDetallesDTO inventarioDetallesDTO) {
        InventarioDetallesDTO nuevoDetalle = inventarioDetallesService.save(inventarioDetallesDTO);
        return ResponseEntity.ok(nuevoDetalle);
    }

    @Operation(summary = "Actualizar un detalle de inventario por ID", description = "Actualiza un detalle de inventario específico por su ID")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<InventarioDetallesDTO> actualizarInventarioDetalle(@PathVariable Integer id, @RequestBody InventarioDetallesDTO inventarioDetallesDTO) {
        InventarioDetallesDTO detalleActualizado = inventarioDetallesService.updateById(inventarioDetallesDTO, id);
        if (detalleActualizado != null) {
            return ResponseEntity.ok(detalleActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar un detalle de inventario por ID", description = "Elimina un detalle de inventario específico por su ID")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarInventarioDetalle(@PathVariable Integer id) {
        inventarioDetallesService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
