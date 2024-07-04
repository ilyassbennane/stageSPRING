package com.ramadan.api.services.produit;

import java.util.Map;

import com.ramadan.api.dto.produit.model.ProduitRequestDto;
import com.ramadan.api.dto.produit.model.ProduitResponseDto;
import com.ramadan.api.dto.produit.model.ProduitUpdateDto;
import com.ramadan.api.exceptions.APIErrorException;

public interface IProduitService {
    ProduitResponseDto findByUuid(String uuid) throws APIErrorException;

     Map<String, Object> findAll(int page, int size, String[] sort) throws APIErrorException;

    ProduitResponseDto saveEntity(ProduitRequestDto requestDto) throws APIErrorException;

    ProduitResponseDto updateEntity(String uuid, ProduitUpdateDto updateDto);

    void deleteProduit(String uuid) throws APIErrorException;

    Map<String, Object> findAllByCategorie(String uuid,int page, int size, String[] sort) throws APIErrorException;

}
