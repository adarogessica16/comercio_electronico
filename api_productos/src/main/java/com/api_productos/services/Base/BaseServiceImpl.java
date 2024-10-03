package com.api_productos.services.base;


import com.beansdto.domain.base.BaseDomain;
import com.beansdto.dtos.base.BaseDTO;
import com.beansdto.dtos.base.BaseResult;

public abstract class BaseServiceImpl<DTO extends BaseDTO, DOMAIN extends BaseDomain,  RESULT extends BaseResult<DTO>> implements IBaseService<DTO,   RESULT> {

    protected abstract DTO convertDomainToDto(DOMAIN domain);

    protected abstract DOMAIN convertDtoToDomain(DTO dto);

}