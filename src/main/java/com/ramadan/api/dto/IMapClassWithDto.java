package com.ramadan.api.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;

public interface IMapClassWithDto<E, D> {

	D convertToDto(E entity, Class<D> dtoClass);

	E convertToEntity(D dto, Class<E> entityClass);

	List<D> convertListToListDto(Collection<E> entityList, Class<D> outCLass);

	List<E> convertListToListEntity(Collection<D> dtoList, Class<E> outCLass);

    Page<D> convertPageToPageDto(Page<E> entityList, Page<D> outCLass);
}
