package com.ramadan.api.services.produit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ramadan.api.dto.MapClassWithDto;
import com.ramadan.api.dto.produit.model.ProduitRequestDto;
import com.ramadan.api.dto.produit.model.ProduitResponseDto;
import com.ramadan.api.dto.produit.model.ProduitUpdateDto;
import com.ramadan.api.dto.produit.service.IProduitMapperService;
import com.ramadan.api.entity.product.Product;
import com.ramadan.api.entity.product.ProductCategory;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ErrorCode;
import com.ramadan.api.helpers.Utils;
import com.ramadan.api.repository.product.CategorieRepository;
import com.ramadan.api.repository.product.ProduitRepository;

@Service
@Transactional
public class ProduitService implements IProduitService {
    @Autowired
    private MapClassWithDto<Product, ProduitRequestDto> mapClassWithRequestDto;

    @Autowired
    private MapClassWithDto<Product, ProduitResponseDto> mapClassWithResponseDto;

    @Autowired
    private ProduitRepository repository;

    @Autowired
    private CategorieRepository categorieRepository;

    
   @Override
    public ProduitResponseDto findByUuid(String uuid) throws APIErrorException {
        Product produit = repository.findByUuid(uuid);
        if (produit != null) {
            return mapClassWithResponseDto.convertToDto(produit,ProduitResponseDto.class);
        }
        else {
            throw new APIErrorException(ErrorCode.A007);
        }
    }
    @Override
    public Map<String, Object> findAllByCategorie(String uuid,int page, int size, String[] sort) throws APIErrorException {
        ProductCategory categorie = categorieRepository.findByUuid(uuid);
        if (categorie == null) {
            throw new APIErrorException(ErrorCode.A008);
        }
        else  {
             List<Order> orders = Utils.getListOrderBySort(sort);
            Pageable pageable = PageRequest.of(page, size,Sort.by(orders));
           Page<Product> product = repository.findAllByCategorie(uuid,pageable);
    
        if (!product.hasContent())  {
            throw new APIErrorException(ErrorCode.A511);
        }
        else{
            List<ProduitResponseDto> productList = product.getContent().stream()
                    .map(product1 -> mapClassWithResponseDto.convertToDto(product1, ProduitResponseDto.class))
                    .collect(Collectors.toList());
    
            Map<String, Object> response = new HashMap<>();
            response.put("missions", productList);
            response.put("currentPage", product.getNumber());
            response.put("totalItems", product.getTotalElements());
            response.put("totalPages", product.getTotalPages());
    
            return response;
        } 
        }
        
    }
     @Override
    public Map<String, Object> findAll(int page, int size, String[] sort) throws APIErrorException {
        List<Order> orders = Utils.getListOrderBySort(sort);
        Pageable pageable = PageRequest.of(page, size,Sort.by(orders));
        Page<Product> product = repository.findAll(pageable);
            List<ProduitResponseDto> productList = product.getContent().stream()
                    .map(product1 -> mapClassWithResponseDto.convertToDto(product1, ProduitResponseDto.class))
                    .collect(Collectors.toList());
    
            Map<String, Object> response = new HashMap<>();
            response.put("missions", productList);
            response.put("currentPage", product.getNumber());
            response.put("totalItems", product.getTotalElements());
            response.put("totalPages", product.getTotalPages());
    
            return response;
      
    }
    @Override
    public ProduitResponseDto saveEntity(ProduitRequestDto requestDto) throws APIErrorException {
        if (requestDto != null) {
            ProductCategory categorie = categorieRepository.findByUuid(requestDto.getCategorieUuid());
            if (categorie == null) {
                throw new APIErrorException(ErrorCode.A008);
            }
            else {
                Product produit = mapClassWithRequestDto.convertToEntity(requestDto, Product.class);
                produit.setProductCategory(categorie);
                Product savedProduit = repository.save(produit);
                return mapClassWithResponseDto.convertToDto(savedProduit, ProduitResponseDto.class);
            }

        }
        else {
            throw new APIErrorException(ErrorCode.A507);
        }
    }
    @Override
    public ProduitResponseDto updateEntity(String uuid, ProduitUpdateDto updateDto) {
        Product existingProduit = repository.findByUuid(uuid);
        if (existingProduit != null) {
            if (updateDto.getDescription() != null && !updateDto.getDescription().isEmpty()) {
                existingProduit.setDescription(updateDto.getDescription());
            }
            if (updateDto.getName() != null && !updateDto.getName().isEmpty()) {
                existingProduit.setName(updateDto.getName());
            }
            // Save the updated entity
            Product updatedProduit = repository.save(existingProduit);
            return mapClassWithResponseDto.convertToDto(updatedProduit,ProduitResponseDto.class);
        }
        return null;
    }
        
    @Override
    public void deleteProduit(String uuid) throws APIErrorException {
        Product produit = repository.findByUuid(uuid);
        if (produit != null) {
            produit.setDelete(true);
            repository.save(produit);
        }
        else {
            throw new APIErrorException(ErrorCode.A007);
        }
    }


}

