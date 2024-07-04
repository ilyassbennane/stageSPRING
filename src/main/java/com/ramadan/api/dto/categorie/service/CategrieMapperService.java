package com.ramadan.api.dto.categorie.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.ramadan.api.dto.categorie.model.CategorieResponceDto;
import com.ramadan.api.entity.product.ProductCategory;

@Service

public class CategrieMapperService implements ICategrieMapperService{
    private final ModelMapper modelMapper;
    public CategrieMapperService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    @Override
    public Page<CategorieResponceDto> convertToResponseDto(Page<ProductCategory> categories) {
        return categories.map((element) -> modelMapper.map(element, CategorieResponceDto.class));
    }
}
