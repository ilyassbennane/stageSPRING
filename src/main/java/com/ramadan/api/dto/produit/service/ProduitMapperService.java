package com.ramadan.api.dto.produit.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.ramadan.api.dto.produit.model.ProduitResponseDto;
import com.ramadan.api.entity.product.Product;

@Service

public class ProduitMapperService implements IProduitMapperService{

    private final ModelMapper modelMapper;
    public ProduitMapperService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    @Override
    public Page<ProduitResponseDto> convertToResponseDto(Page<Product> produits) {
        return produits.map((element) -> modelMapper.map(element,ProduitResponseDto.class));
    }
}
