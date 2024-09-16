package com.api_usuarios.service.rol;

import com.beansdto.dtos.rol.RolDTO;
import com.api_usuarios.dao.rol.RolDao;
import com.beansdto.domain.rol.RolDomain;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RolServiceImpl implements IRolService {

    private static final Logger logger = LoggerFactory.getLogger(RolServiceImpl.class);
    private final RolDao rolDao;

    @Override
    public List<RolDTO> findAll() {
        logger.info("Buscando todos los roles");
        List<RolDomain> roles = rolDao.findAll();
        if (roles.isEmpty()) {
            logger.info("No se encontraron roles.");
        } else {
            logger.info("Número de roles encontrados: {}", roles.size());
        }
        return roles.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<RolDTO> findAllPaginated(int page, int size) {
        logger.info("Buscando roles paginados - Página: {}, Tamaño: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<RolDomain> rolesPage = rolDao.findAll(pageable);
        if (rolesPage.isEmpty()) {
            logger.info("No se encontraron roles en la página solicitada.");
        } else {
            logger.info("Número de roles encontrados en la página: {}", rolesPage.getNumberOfElements());
        }
        return rolesPage.map(this::convertToDTO);
    }

    @Override
    public RolDTO findById(Integer id) {
        logger.info("Buscando rol con ID: {}", id);
        Optional<RolDomain> rol = rolDao.findById(id);
        if (rol.isPresent()) {
            logger.info("Rol encontrado con ID: {}", id);
            return convertToDTO(rol.get());
        } else {
            logger.info("Rol no encontrado con ID: {}", id);
            return null;
        }
    }

    @Override
    public RolDTO create(RolDTO rolDTO) {
        logger.info("Creando nuevo rol: {}", rolDTO.getNombre());
        RolDomain rol = convertToDomain(rolDTO);
        rol.setId_Rol(null); // Asegurarse de que el Id sea null para que la base de datos lo genere
        RolDomain savedRol = rolDao.save(rol);
        logger.info("Rol creado exitosamente con ID: {}", savedRol.getId_Rol());
        return convertToDTO(savedRol);
    }

    @Override
    public RolDTO update(Integer id, RolDTO rolDTO) {
        logger.info("Actualizando rol con ID: {}", id);
        Optional<RolDomain> optionalRol = rolDao.findById(id);
        if (optionalRol.isPresent()) {
            RolDomain rol = optionalRol.get();
            rol.setNombre(rolDTO.getNombre());
            rol.setDescripcion(rolDTO.getDescripcion());
            RolDomain updatedRol = rolDao.save(rol);
            logger.info("Rol actualizado exitosamente con ID: {}", updatedRol.getId_Rol());
            return convertToDTO(updatedRol);
        } else {
            logger.info("Rol no encontrado con ID: {}", id);
            return null;
        }
    }

    @Override
    public RolDTO patch(Integer id, RolDTO rolDTO) {
        logger.info("Actualizando parcialmente rol con ID: {}", id);
        Optional<RolDomain> optionalRol = rolDao.findById(id);
        if (optionalRol.isPresent()) {
            RolDomain rol = optionalRol.get();
            if (rolDTO.getNombre() != null) {
                rol.setNombre(rolDTO.getNombre());
            }
            if (rolDTO.getDescripcion() != null) {
                rol.setDescripcion(rolDTO.getDescripcion());
            }
            RolDomain updatedRol = rolDao.save(rol);
            logger.info("Rol actualizado parcialmente exitosamente con ID: {}", updatedRol.getId_Rol());
            return convertToDTO(updatedRol);
        } else {
            logger.info("Rol no encontrado con ID: {}", id);
            return null;
        }
    }

    @Override
    public void delete(Integer id) {
        logger.info("Eliminando rol con ID: {}", id);
        try {
            if (rolDao.existsById(id)) {
                rolDao.deleteById(id);
                logger.info("Rol eliminado exitosamente con ID: {}", id);
            } else {
                logger.info("Rol no encontrado con ID: {}", id);
            }
        } catch (Exception e) {
            logger.error("Error al eliminar el rol con ID: {}", id, e);
        }
    }

    // Conversión manual a DTO
    private RolDTO convertToDTO(RolDomain rolDomain) {
        RolDTO rolDTO = new RolDTO();
        rolDTO.setId(rolDomain.getId_Rol());
        rolDTO.setNombre(rolDomain.getNombre());
        rolDTO.setDescripcion(rolDomain.getDescripcion());
        return rolDTO;
    }

    // Conversión manual a Domain
    private RolDomain convertToDomain(RolDTO rolDTO) {
        RolDomain rolDomain = new RolDomain();
        rolDomain.setNombre(rolDTO.getNombre());
        rolDomain.setDescripcion(rolDTO.getDescripcion());
        return rolDomain;
    }
}
