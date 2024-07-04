package com.ramadan.api.services.unitProduct;

import java.util.Map;

import com.ramadan.api.dto.produit.ProductUnitConversion.ProductUnitConversionRequestDto;
import com.ramadan.api.dto.produit.ProductUnitConversion.ProductUnitConversionResponseDto;
import com.ramadan.api.dto.produit.ProductUnitConversion.ProductUnitConversionUpdateDto;
import com.ramadan.api.exceptions.APIErrorException;

public interface IProductUnitConversionService {
    
    ProductUnitConversionResponseDto findByUuid(String uuid) throws APIErrorException;

    Map<String, Object> findAll(int page, int size, String[] sort) throws APIErrorException;

    ProductUnitConversionResponseDto saveUnitConversion(ProductUnitConversionRequestDto unitConversionRequestDto) throws APIErrorException;

    ProductUnitConversionResponseDto updateUnitConversion(ProductUnitConversionUpdateDto requestDto) throws APIErrorException;

    void deleteUnitConversion(String uuid) throws APIErrorException;

    Map<String, Object> getByProduct(String productUuid, int page, int size, String[] sort) throws APIErrorException;

}
