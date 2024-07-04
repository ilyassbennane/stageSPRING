package com.ramadan.api.dto.categorie.service;

import org.springframework.data.domain.Page;

import com.ramadan.api.dto.categorie.model.CategorieResponceDto;
import com.ramadan.api.entity.product.ProductCategory;

public interface ICategrieMapperService {
    Page<CategorieResponceDto> convertToResponseDto(Page<ProductCategory> categories);
}
