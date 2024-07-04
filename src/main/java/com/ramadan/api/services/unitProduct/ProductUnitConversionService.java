package com.ramadan.api.services.unitProduct;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ramadan.api.dto.MapClassWithDto;
import com.ramadan.api.dto.produit.ProductUnitConversion.ProductUnitConversionRequestDto;
import com.ramadan.api.dto.produit.ProductUnitConversion.ProductUnitConversionResponseDto;
import com.ramadan.api.dto.produit.ProductUnitConversion.ProductUnitConversionUpdateDto;
import com.ramadan.api.entity.product.Product;
import com.ramadan.api.entity.product.ProductUnitConversion;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ErrorCode;
import com.ramadan.api.helpers.Utils;
import com.ramadan.api.repository.product.ProductUnitConversionRepository;
import com.ramadan.api.repository.product.ProduitRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductUnitConversionService implements IProductUnitConversionService {

    @Autowired
    private ProductUnitConversionRepository productUnitConversionRepository;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private MapClassWithDto<ProductUnitConversion, ProductUnitConversionRequestDto> mapClassWithRequestDto;

    @Autowired
    private MapClassWithDto<ProductUnitConversion, ProductUnitConversionResponseDto> mapClassWithResponseDto;

    @Override
    public ProductUnitConversionResponseDto findByUuid(String uuid) throws APIErrorException {
        ProductUnitConversion unitConversion = productUnitConversionRepository.findByUuid(uuid);
        if (unitConversion == null) {
            throw new APIErrorException(ErrorCode.A001);
        } else {
            return mapClassWithResponseDto.convertToDto(unitConversion, ProductUnitConversionResponseDto.class);
        }
    }

    @Override
    public Map<String, Object> findAll(int page, int size, String[] sort) throws APIErrorException {
        List<Sort.Order> orders = Utils.getListOrderBySort(sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
        Page<ProductUnitConversion> unitConversionPage = productUnitConversionRepository.findAll(pageable);

        List<ProductUnitConversionResponseDto> unitConversions = unitConversionPage.getContent().stream()
                .map(unitConversion -> mapClassWithResponseDto.convertToDto(unitConversion, ProductUnitConversionResponseDto.class))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("unitConversions", unitConversions);
        response.put("currentPage", unitConversionPage.getNumber());
        response.put("totalItems", unitConversionPage.getTotalElements());
        response.put("totalPages", unitConversionPage.getTotalPages());

        return response;
    }

    @Override
    public ProductUnitConversionResponseDto saveUnitConversion(ProductUnitConversionRequestDto unitConversionRequestDto) throws APIErrorException {
        if (unitConversionRequestDto != null) {
        Product product = produitRepository.findByUuid(unitConversionRequestDto.getProductUuid());
        if (product == null) {
            throw new APIErrorException(ErrorCode.A007);
        }
        else{
            ProductUnitConversion unitConversion = mapClassWithRequestDto.convertToEntity(unitConversionRequestDto, ProductUnitConversion.class);
            unitConversion.setProduct(product);
            ProductUnitConversion savedUnitConversion = productUnitConversionRepository.save(unitConversion);
            return mapClassWithResponseDto.convertToDto(savedUnitConversion, ProductUnitConversionResponseDto.class);
        } 
    }
        else {
            throw new APIErrorException(ErrorCode.A507);
        }
    }
    

    @Override
public ProductUnitConversionResponseDto updateUnitConversion(ProductUnitConversionUpdateDto requestDto) throws APIErrorException {
    ProductUnitConversion existingUnitConversion = productUnitConversionRepository.findByUuid(requestDto.getUuid());
    if (existingUnitConversion == null) {
        throw new APIErrorException(ErrorCode.A507);
    } else {
        if (requestDto.getRapportConversion() != null) {
            existingUnitConversion.setRapportConversion(requestDto.getRapportConversion());
        }
        if (requestDto.getCodeUnit() != null && !requestDto.getCodeUnit().isEmpty()) {
            existingUnitConversion.setCodeUnit(requestDto.getCodeUnit());
        }
        if (requestDto.getName() != null && !requestDto.getName().isEmpty()) {
            existingUnitConversion.setName(requestDto.getName());
        }
        if (requestDto.getDescription() != null) {
            existingUnitConversion.setDescription(requestDto.getDescription());
        }

        ProductUnitConversion updatedUnitConversion = productUnitConversionRepository.save(existingUnitConversion);
        return mapClassWithResponseDto.convertToDto(updatedUnitConversion, ProductUnitConversionResponseDto.class);
    }
}

@Override
public void deleteUnitConversion(String uuid) throws APIErrorException {
    ProductUnitConversion unitConversion = productUnitConversionRepository.findByUuid(uuid);
    if (unitConversion != null) {
        productUnitConversionRepository.delete(unitConversion);
    } else {
        throw new APIErrorException(ErrorCode.A001);
    }
}

@Override   
public Map<String, Object> getByProduct(String productUuid, int page, int size, String[] sort) throws APIErrorException {
    Page<ProductUnitConversion> unitConversionsPage = productUnitConversionRepository.findByProductUuid(productUuid, PageRequest.of(page, size, Sort.by(sort)));
    if (unitConversionsPage.hasContent()) {
        List<ProductUnitConversionResponseDto> unitConversions = unitConversionsPage.getContent().stream()
                .map(unitConversion -> mapClassWithResponseDto.convertToDto(unitConversion, ProductUnitConversionResponseDto.class))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("unitConversions", unitConversions);
        response.put("currentPage", unitConversionsPage.getNumber());
        response.put("totalItems", unitConversionsPage.getTotalElements());
        response.put("totalPages", unitConversionsPage.getTotalPages());

        return response;
    } else {
        throw new APIErrorException(ErrorCode.A003); // Ou tout autre code d'erreur approprié
    }
}




}
