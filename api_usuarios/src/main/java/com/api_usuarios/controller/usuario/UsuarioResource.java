package com.api_usuarios.controller.usuario;

import com.beansdto.dtos.usuario.UsuarioDTO;
import com.api_usuarios.service.usuario.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioResource {

    private final IUsuarioService usuarioService; // Usa la interfaz IUsuarioService

    @GetMapping
    public ResponseEntity<?> getAllUsuarios(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {

        if (page == null || size == null) {
            // Si no se proporcionan parámetros de paginación, retorna todos los usuarios sin paginación
            List<UsuarioDTO> usuarios = usuarioService.findAll();
            return ResponseEntity.ok(usuarios);
        } else {
            // Si se proporcionan los parámetros de paginación
            Page<UsuarioDTO> usuariosPage = usuarioService.findAllPaginated(page, size);
            return ResponseEntity.ok(usuariosPage);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable Integer id) {
        UsuarioDTO usuario = usuarioService.findById(id);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<UsuarioDTO>> searchUsuariosByNombre(@PathVariable String nombre) {
        List<UsuarioDTO> usuarios = usuarioService.findByNombre(nombre);

        if (usuarios.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Devuelve un 404 si no hay resultados
        }

        return ResponseEntity.ok(usuarios); // Devuelve los resultados encontrados
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> createUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO createdUsuario = usuarioService.create(usuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUsuario);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<UsuarioDTO> updateUsuario(@PathVariable Integer id, @RequestBody UsuarioDTO usuarioDTO) {
        try {
            UsuarioDTO updatedUsuario = usuarioService.update(id, usuarioDTO);
            return ResponseEntity.ok(updatedUsuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id) {
        try {
            usuarioService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
