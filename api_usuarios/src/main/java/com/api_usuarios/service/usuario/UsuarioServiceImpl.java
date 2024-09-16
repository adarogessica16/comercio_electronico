package com.api_usuarios.service.usuario;

import com.beansdto.dtos.usuario.UsuarioDTO;
import com.beansdto.dtos.rol.RolDTO;
import com.api_usuarios.dao.usuario.UsuarioDao;
import com.api_usuarios.dao.rol.RolDao;
import com.beansdto.domain.usuario.UsuarioDomain;
import com.beansdto.domain.rol.RolDomain;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);
    private final UsuarioDao usuarioDao;
    private final RolDao rolDao; // Dependencia para acceder a roles
    private final ObjectMapper objectMapper;

    @Transactional
    @Override
    public List<UsuarioDTO> findAll() {
        logger.info("Buscando todos los usuarios");
        List<UsuarioDomain> usuarios = usuarioDao.findAll();
        logger.info("Número de usuarios encontrados: {}", usuarios.size());

        for (UsuarioDomain usuario : usuarios) {
            logger.info("Usuario: {}, Roles: {}", usuario.getNombre(), usuario.getRoles());
        }

        return usuarios.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    @Override
    public Page<UsuarioDTO> findAllPaginated(int page, int size) {
        logger.info("Buscando usuarios paginados - Página: {}, Tamaño: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<UsuarioDomain> usuariosPage = usuarioDao.findAll(pageable); // Aquí usamos paginación
        if (usuariosPage.isEmpty()) {
            logger.info("No se encontraron usuarios en la página solicitada.");
        } else {
            logger.info("Número de usuarios encontrados en la página: {}", usuariosPage.getNumberOfElements());
        }
        return usuariosPage.map(this::convertToDTO); // Convertimos a DTO y devolvemos la estructura paginada
    }

    @Override
    public UsuarioDTO findById(Integer id) {
        Optional<UsuarioDomain> usuario = usuarioDao.findById(id);
        return usuario.map(this::convertToDTO).orElse(null);
    }

    @Override
    public List<UsuarioDTO> findByNombre(String nombre) {
        logger.info("Buscando usuarios por nombre: {}", nombre);
        List<UsuarioDomain> usuarios = usuarioDao.findByNombreContainingIgnoreCase(nombre);

        if (usuarios.isEmpty()) {
            logger.info("No se encontraron usuarios con el nombre: {}", nombre);
        } else {
            logger.info("Número de usuarios encontrados con nombre '{}': {}", nombre, usuarios.size());
        }

        return usuarios.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public UsuarioDTO create(UsuarioDTO usuarioDTO) {
        logger.info("Creando nuevo usuario: {}", usuarioDTO.getNombre());
        UsuarioDomain usuario = convertToDomain(usuarioDTO);

        Set<RolDomain> roles = usuarioDTO.getRoles().stream()
                .map(rolDTO -> rolDao.findById(rolDTO.getId())
                        .orElseThrow(() -> {
                            logger.info("Rol con ID {} no encontrado", rolDTO.getId());
                            return new RuntimeException("Rol con ID " + rolDTO.getId() + " no encontrado");
                        }))
                .collect(Collectors.toSet());
        usuario.setRoles(roles);

        UsuarioDomain savedUsuario = usuarioDao.save(usuario);
        logger.info("Usuario creado exitosamente con ID: {}", savedUsuario.getId_Usuario());
        return convertToDTO(savedUsuario);
    }

    @Override
    public UsuarioDTO update(Integer id, UsuarioDTO usuarioDTO) {
        logger.info("Actualizando usuario con ID: {}", id);
        try {
            Optional<UsuarioDomain> optionalUsuario = usuarioDao.findById(id);
            if (optionalUsuario.isPresent()) {
                UsuarioDomain usuario = optionalUsuario.get();

                String apellidoActual = usuario.getApellido();

                objectMapper.updateValue(usuario, usuarioDTO);

                if (usuarioDTO.getApellido() == null) {
                    usuario.setApellido(apellidoActual);
                }

                if (usuarioDTO.getRoles() != null) {
                    Set<RolDomain> roles = usuarioDTO.getRoles().stream()
                            .map(rolDTO -> rolDao.findById(rolDTO.getId())
                                    .orElseThrow(() -> {
                                        logger.info("Rol con ID {} no encontrado", rolDTO.getId());
                                        return new RuntimeException("Rol con ID " + rolDTO.getId() + " no encontrado");
                                    }))
                            .collect(Collectors.toSet());
                    usuario.setRoles(roles);
                }

                UsuarioDomain updatedUsuario = usuarioDao.save(usuario);
                logger.info("Usuario actualizado exitosamente con ID: {}", updatedUsuario.getId_Usuario());
                return convertToDTO(updatedUsuario);
            } else {
                logger.info("Usuario no encontrado con ID: {}", id);
                throw new RuntimeException("Usuario no encontrado con ID: " + id);
            }
        } catch (JsonMappingException e) {
            logger.info("Error en el formato de datos: {}", e.getMessage());
            throw new RuntimeException("Error en el formato de datos: " + e.getMessage());
        } catch (RuntimeException e) {
            logger.info("Error en los roles: {}", e.getMessage());
            throw new RuntimeException("Error en los roles: " + e.getMessage());
        } catch (Exception e) {
            logger.info("Error inesperado: {}", e.getMessage());
            throw new RuntimeException("Error inesperado: " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public ResponseEntity<String> delete(Integer id) {
        logger.info("Eliminando usuario con ID: {}", id);
        try {
            if (usuarioDao.existsById(id)) {
                usuarioDao.deleteById(id);
                logger.info("Usuario eliminado con éxito.");
                return ResponseEntity.ok("Usuario eliminado con éxito.");
            } else {
                logger.info("Usuario no encontrado con ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado con ID: " + id);
            }
        } catch (Exception e) {
            logger.info("Error al eliminar el usuario con ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el usuario: " + e.getMessage());
        }
    }

    // Métodos de conversión
    private UsuarioDTO convertToDTO(UsuarioDomain usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId_Usuario());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setApellido(usuario.getApellido());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setContraseña(usuario.getContraseña());
        usuarioDTO.setTelefono(usuario.getTelefono());
        usuarioDTO.setDireccion(usuario.getDireccion());

        Set<RolDTO> rolesDTO = usuario.getRoles().stream()
                .map(this::convertRolToDTO)
                .collect(Collectors.toSet());
        usuarioDTO.setRoles(rolesDTO);

        return usuarioDTO;
    }

    private UsuarioDomain convertToDomain(UsuarioDTO usuarioDTO) {
        UsuarioDomain usuario = new UsuarioDomain();
        usuario.setId_Usuario(usuarioDTO.getId());
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setContraseña(usuarioDTO.getContraseña());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setDireccion(usuarioDTO.getDireccion());

        Set<RolDomain> roles = usuarioDTO.getRoles().stream()
                .map(this::convertRolToDomain)
                .collect(Collectors.toSet());
        usuario.setRoles(roles);

        return usuario;
    }

    private RolDomain convertRolToDomain(RolDTO rolDTO) {
        return rolDao.findById(rolDTO.getId())
                .orElseThrow(() -> new RuntimeException("Rol con ID " + rolDTO.getId() + " no encontrado"));
    }

    private RolDTO convertRolToDTO(RolDomain rolDomain) {
        RolDTO rolDTO = new RolDTO();
        rolDTO.setId(rolDomain.getId_Rol());
        rolDTO.setNombre(rolDomain.getNombre());
        rolDTO.setDescripcion(rolDomain.getDescripcion());
        return rolDTO;
    }
}
