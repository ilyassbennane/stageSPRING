package com.ramadan.api.services.brand;

import java.util.Map;

import com.ramadan.api.dto.brand.BrandDto;
import com.ramadan.api.dto.brand.BrandRequestDto;
import com.ramadan.api.dto.brand.BrandResponseDto;
import com.ramadan.api.dto.brand.BrandSearchRequestDto;
import com.ramadan.api.dto.brand.BrandUpdateDto;
import com.ramadan.api.entity.product.Brand;
import com.ramadan.api.exceptions.APIErrorException;

public interface IBrandService {
	BrandDto getBrandByUuid(String uuid) throws APIErrorException;

    Map<String, Object> getAllByCompany(String companyUuid, int page, int size, String[] sort) throws APIErrorException;

    BrandResponseDto saveBrand(BrandRequestDto brandRequestDto) throws APIErrorException;

    BrandResponseDto updateBrand( BrandUpdateDto requestDto) throws APIErrorException;

    void deleteBrand(String uuid) throws APIErrorException;

    Map<String, Object> getAllBrands(int page, int size, String[] sort) throws APIErrorException;
    
    Brand checkBrandUuid(String uuid) throws APIErrorException;
    Map<String, Object> search(BrandSearchRequestDto searchRequestDto, int page, int size, String[] sort)
			throws APIErrorException;


}
