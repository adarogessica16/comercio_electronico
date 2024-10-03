package com.api_productos.services.InventarioDetalles;

import com.api_productos.dao.InventarioDetalles.IInventarioDetalles;
import com.api_productos.dao.inventario.IInventario;
import com.api_productos.dao.producto.IProducto;
import com.api_productos.services.base.BaseServiceImpl;
import com.beansdto.domain.InventarioDetalles.InventarioDetallesDomain;
import com.beansdto.domain.inventario.InventarioDomain;
import com.beansdto.domain.producto.ProductoDomain;
import com.beansdto.dtos.inventarioDetalles.InventarioDetallesDTO;
import com.beansdto.dtos.inventarioDetalles.InventarioDetallesResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventarioDetallesServiceImpl extends BaseServiceImpl<InventarioDetallesDTO, InventarioDetallesDomain, InventarioDetallesResult> implements IInventarioDetallesBase {

    @Autowired
    private IInventarioDetalles inventarioDetallesDao;

    @Autowired
    private IProducto productoDao;

    @Autowired
    private IInventario inventarioDao;

    @Override
    @Transactional
    public InventarioDetallesDTO save(InventarioDetallesDTO dto) {

        InventarioDetallesDomain domain = convertDtoToDomain(dto);
        InventarioDetallesDomain savedDetalles = inventarioDetallesDao.save(domain);
        return convertDomainToDto(savedDetalles);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        inventarioDetallesDao.deleteById(id);
    }

    @Override
    @Transactional
    public InventarioDetallesDTO getById(Integer id) {
        return inventarioDetallesDao.findById(id)
                .map(this::convertDomainToDto)
                .orElse(null);
    }

    @Override
    public InventarioDetallesResult getAll(Pageable pageable){
        Page<InventarioDetallesDomain> page = inventarioDetallesDao.findAll(pageable);
        List<InventarioDetallesDTO> detalles = page.getContent()
                .stream()
                .map(this::convertDomainToDto)
                .collect(Collectors.toList());
        InventarioDetallesResult result = new InventarioDetallesResult();
        result.setInventarioDetalles(detalles);
        return result;
    }

    @Override
    @Transactional
    public InventarioDetallesDTO updateById(InventarioDetallesDTO dto, Integer id) {
        if (dto == null) {
            throw new IllegalArgumentException("El InventarioDetallesDTO no puede ser nulo");
        }
        Optional<InventarioDetallesDomain> inventarioDetalleOpcional = inventarioDetallesDao.findById(id);

        if (inventarioDetalleOpcional.isEmpty()) {
            throw new RuntimeException("Detalle de inventario no encontrado con ID: " + id);
        }
        // Obtener el detalle existente
        InventarioDetallesDomain inventarioDetalleExistente = inventarioDetalleOpcional.get();
        // Actualizar los campos
        inventarioDetalleExistente.setCantidad(dto.getCantidad());
        if (!inventarioDetalleExistente.getProducto().getIdProducto().equals(dto.getIdProducto())) {
            ProductoDomain nuevoProducto = obtenerProducto(dto.getIdProducto());
            inventarioDetalleExistente.setProducto(nuevoProducto);
        }
        InventarioDetallesDomain inventarioDetalleActualizado = inventarioDetallesDao.save(inventarioDetalleExistente);
        return convertDomainToDto(inventarioDetalleActualizado);
    }


    @Override
    protected InventarioDetallesDTO convertDomainToDto(InventarioDetallesDomain domain) {
        if (domain == null) {
            return null;
        }
        InventarioDetallesDTO dto = new InventarioDetallesDTO();
        dto.setId(domain.getIdDetalle());
        dto.setIdInventario(domain.getInventario().getIdInventario());
        dto.setIdProducto(domain.getProducto().getIdProducto());
        dto.setCantidad(domain.getCantidad());
        return dto;
    }

    @Override
    protected InventarioDetallesDomain convertDtoToDomain(InventarioDetallesDTO dto) {
        if (dto == null) {
            return null;
        }
        InventarioDetallesDomain domain = new InventarioDetallesDomain();
        domain.setIdDetalle(dto.getId());
        InventarioDomain inventario = obtenerInventario(dto.getIdInventario());
        domain.setInventario(inventario);
        ProductoDomain producto = obtenerProducto(dto.getIdProducto());
        domain.setProducto(producto);
        domain.setCantidad(dto.getCantidad());
        return domain;
    }

    public ProductoDomain obtenerProducto(Integer id) {
        Optional<ProductoDomain> producto = productoDao.findById(id);
        return producto.orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

    }

    public InventarioDomain obtenerInventario(Integer id) {
        Optional<InventarioDomain> inventario = inventarioDao.findById(id);
        return inventario.orElseThrow(() -> new RuntimeException("Inventario no encontrado con ID: " + id));
    }

}