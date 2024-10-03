package com.api_productos.services.inventario;

import com.api_productos.dao.inventario.IInventario;
import com.api_productos.dao.producto.IProducto;
import com.api_productos.dao.usuarios.IUsuario;
import com.api_productos.services.producto.ProductoServiceImpl;
import com.beansdto.domain.InventarioDetalles.InventarioDetallesDomain;
import com.beansdto.domain.inventario.InventarioDomain;
import com.beansdto.domain.producto.ProductoDomain;
import com.beansdto.domain.usuario.UsuarioDomain;
import com.beansdto.dtos.inventario.InventarioDTO;
import com.beansdto.dtos.inventario.InventarioResult;
import com.api_productos.services.base.BaseServiceImpl;
import com.beansdto.dtos.inventarioDetalles.InventarioDetallesDTO;
import org.springframework.beans.factory.annotation.Autowired;
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
public class InventarioServiceImpl extends BaseServiceImpl<InventarioDTO, InventarioDomain, InventarioResult> implements IInventarioBase {

    @Autowired
    private IInventario inventarioDao;

    @Autowired
    private IProducto productoDao;
    @Autowired
    private IUsuario usuarioDao;

    @Autowired
    private ProductoServiceImpl servicesProducto;

    @Transactional
    @Override
    @CachePut(value = "inventarios", key = "#result.id")
    public InventarioDTO save(InventarioDTO dto) {
        InventarioDomain inventarioDomain = convertDtoToDomain(dto);
        InventarioDomain savedInventario = inventarioDao.save(inventarioDomain);
        return convertDomainToDto(savedInventario);
    }


    @Override
    public void deleteById(Integer id) {
        inventarioDao.deleteById(id);
    }

    @Transactional
    @Override
    @Cacheable(value = "inventarios", key = "#id")
    public InventarioDTO getById(Integer id) {
        return inventarioDao.findById(id)
                .map(this::convertDomainToDto)
                .orElse(null);
    }

    @Override
    @Transactional
    public InventarioResult getAll(Pageable pageable) {
        Page<InventarioDomain> page = inventarioDao.findAll(pageable);
        List<InventarioDTO> inventarios = page.getContent()
                .stream()
                .map(this::convertDomainToDto)
                .collect(Collectors.toList());
        InventarioResult result = new InventarioResult();
        result.setInventarios(inventarios);

        return result;
    }

    @Override
    @Transactional
    public InventarioDTO updateById(InventarioDTO dto, Integer id) {
        if (dto == null) {
            throw new IllegalArgumentException("El InventarioDTO no puede ser nulo");
        }

        Optional<InventarioDomain> inventarioOpcional = inventarioDao.findById(id);
        if (inventarioOpcional.isEmpty()) {
            throw new RuntimeException("Inventario no encontrado con ID: " + id);
        }
        InventarioDomain inventarioExistente = inventarioOpcional.get();

        // Actualizar el usuario si ha cambiado
        if (!inventarioExistente.getUsuario().getId_Usuario().equals(dto.getIdUsuario())) {
            UsuarioDomain nuevoUsuario = servicesProducto.obtenerUsuario(dto.getIdUsuario());
            inventarioExistente.setUsuario(nuevoUsuario);
        }
        inventarioExistente.setFecha(dto.getFecha());
        if (dto.getDetalles() != null) {
            List<InventarioDetallesDomain> detallesDomain = dto.getDetalles().stream()
                    .map(detalleDTO -> {
                        InventarioDetallesDomain detalleDomain = new InventarioDetallesDomain();
                        ProductoDomain productoDomain =obtenerProducto(detalleDTO.getIdProducto());
                        detalleDomain.setProducto(productoDomain);
                        detalleDomain.setCantidad(detalleDTO.getCantidad());
                        return detalleDomain;
                    })
                    .collect(Collectors.toList());
            inventarioExistente.setDetalles(detallesDomain);
        }

        InventarioDomain inventarioActualizado = inventarioDao.save(inventarioExistente);
        return convertDomainToDto(inventarioActualizado);
    }

    @Override
    protected InventarioDTO convertDomainToDto(InventarioDomain domain) {
        if (domain == null) {
            return null;
        }

        InventarioDTO dto = new InventarioDTO();
        dto.setId(domain.getIdInventario());
        dto.setIdUsuario(domain.getUsuario().getId_Usuario());
        dto.setFecha(domain.getFecha());

        if (domain.getDetalles() != null) {
            List<InventarioDetallesDTO> inventarioDetallesDTO = domain.getDetalles().stream()
                    .map(detalle -> {
                        InventarioDetallesDTO detalleDTO = new InventarioDetallesDTO();
                        detalleDTO.setIdInventario(detalle.getInventario().getIdInventario());
                        detalleDTO.setIdProducto(detalle.getProducto().getIdProducto());
                        detalleDTO.setCantidad(detalle.getCantidad());
                        detalleDTO.setId(detalle.getIdDetalle());
                        return detalleDTO;
                    })
                    .collect(Collectors.toList());

            dto.setDetalles(inventarioDetallesDTO);
        }

        return dto;
    }
    @Override
    protected InventarioDomain convertDtoToDomain(InventarioDTO dto) {
        if (dto == null) {
            return null;
        }
        InventarioDomain domain = new InventarioDomain();
        domain.setIdInventario(dto.getId());

        // Obtener el usuario del DTO y asignarlo al dominio
        UsuarioDomain usuarioDomain = servicesProducto.obtenerUsuario(dto.getIdUsuario());
        domain.setUsuario(usuarioDomain);
        domain.setFecha(dto.getFecha());

        // Convertir detalles del DTO al dominio
        if (dto.getDetalles() != null) {
            List<InventarioDetallesDomain> detallesDomain = dto.getDetalles().stream()
                    .map(detalleDTO -> {
                        InventarioDetallesDomain detalleDomain = new InventarioDetallesDomain();
                        ProductoDomain productoDomain =obtenerProducto(dto.getId());
                        detalleDomain.setProducto(productoDomain);
                        detalleDomain.setCantidad(detalleDTO.getCantidad());
                        return detalleDomain;
                    })
                    .collect(Collectors.toList());
            domain.setDetalles(detallesDomain);
        }


        return domain;
    }
    public ProductoDomain obtenerProducto(Integer id){
        Optional<ProductoDomain> usuario = productoDao.findById(id);
        return usuario.orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

    }
}



