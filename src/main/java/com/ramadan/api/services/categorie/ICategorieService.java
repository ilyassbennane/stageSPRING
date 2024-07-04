package com.ramadan.api.services.categorie;

import java.util.Map;

import com.ramadan.api.dto.categorie.model.CategorieRequestDto;
import com.ramadan.api.dto.categorie.model.CategorieResponceDto;
import com.ramadan.api.dto.categorie.model.CategorieUpdateDto;
import com.ramadan.api.dto.categorie.model.CategoryChildDto;
import com.ramadan.api.exceptions.APIErrorException;

public interface ICategorieService {
    CategorieResponceDto findByUuid(String uuid) throws APIErrorException;

    Map<String, Object> findAllByCategorieParente(String uuid, int page, int size, String[] sort) throws APIErrorException;

    CategorieResponceDto saveEntity(CategorieRequestDto requestDto) throws APIErrorException;

    CategorieResponceDto saveEntityChild(CategoryChildDto requestDto) throws APIErrorException;

    CategorieResponceDto updateEntity(String uuid, CategorieUpdateDto updateDto) throws APIErrorException;

    void deleteCategorie(String uuid) throws APIErrorException;

    Map<String, Object> findAllByCompany(String uuid, int page, int size, String[] sort) throws APIErrorException;

    Map<String, Object> getAllCategories(int offset, int pageSize, String[] sort) throws APIErrorException;
}
