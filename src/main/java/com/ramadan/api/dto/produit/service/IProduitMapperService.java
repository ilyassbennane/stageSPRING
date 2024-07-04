package com.ramadan.api.dto.produit.service;

import org.springframework.data.domain.Page;

import com.ramadan.api.dto.produit.model.ProduitResponseDto;
import com.ramadan.api.entity.product.Product;

public interface IProduitMapperService {
    Page<ProduitResponseDto> convertToResponseDto(Page<Product> produits);
}
