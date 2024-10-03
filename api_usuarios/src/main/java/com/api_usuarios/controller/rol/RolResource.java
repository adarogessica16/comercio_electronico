package com.api_usuarios.controller.rol;

import com.beansdto.dtos.rol.RolDTO;
import com.api_usuarios.service.rol.IRolService;  // Cambiado de RolService a IRolService
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RolResource {

    private final IRolService rolService;  // Cambiado de RolService a IRolService

    // Obtener roles con o sin paginación
    @GetMapping
    public ResponseEntity<?> getAllRoles(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {

        // Si no se proveen parámetros de paginación, retorna todos los roles sin paginación
        if (page == null || size == null) {
            List<RolDTO> roles = rolService.findAll();
            return ResponseEntity.ok(roles);
        }

        // Si se proveen los parámetros de paginación
        Page<RolDTO> rolesPage = rolService.findAllPaginated(page, size);
        return ResponseEntity.ok(rolesPage);
    }

    // Obtener un rol por ID
    @GetMapping("/{id}")
    public ResponseEntity<RolDTO> getRolById(@PathVariable Integer id) {
        RolDTO rol = rolService.findById(id);
        if (rol != null) {
            return ResponseEntity.ok(rol);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Crear un nuevo rol
    @PostMapping
    public ResponseEntity<RolDTO> createRol(@RequestBody RolDTO rolDTO) {
        RolDTO createdRol = rolService.create(rolDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRol);
    }

    // Actualizar un rol por completo
    @PutMapping("/{id}")
    public ResponseEntity<RolDTO> updateRol(@PathVariable Integer id, @RequestBody RolDTO rolDTO) {
        RolDTO updatedRol = rolService.update(id, rolDTO);
        if (updatedRol != null) {
            return ResponseEntity.ok(updatedRol);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Actualizar parcialmente un rol (PATCH)
    @PatchMapping("/{id}")
    public ResponseEntity<RolDTO> patchRol(@PathVariable Integer id, @RequestBody RolDTO rolDTO) {
        RolDTO updatedRol = rolService.patch(id, rolDTO);
        if (updatedRol != null) {
            return ResponseEntity.ok(updatedRol);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Eliminar un rol
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRol(@PathVariable Integer id) {
        try {
            rolService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
