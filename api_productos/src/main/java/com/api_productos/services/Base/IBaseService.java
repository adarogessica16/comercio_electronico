package com.api_productos.services.base;


import org.springframework.data.domain.Pageable;
import com.beansdto.dtos.base.BaseDTO;
import com.beansdto.dtos.base.BaseResult;

public interface IBaseService<DTO extends BaseDTO, R extends BaseResult<DTO>> {
    public DTO save(DTO dto);
    public void deleteById(Integer id);
    public DTO getById(Integer id);
    public DTO updateById(DTO dto, Integer id);
    R getAll(Pageable pageable);
}
