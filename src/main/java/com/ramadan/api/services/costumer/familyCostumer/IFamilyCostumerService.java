package com.ramadan.api.services.costumer.familyCostumer;

import java.util.List;
import java.util.Map;

import com.ramadan.api.dto.familyCostumer.model.FamilyCostumerDto;
import com.ramadan.api.dto.familyCostumer.model.FamilyCostumerRequestDto;
import com.ramadan.api.dto.familyCostumer.model.FamilyCostumerResponseDto;
import com.ramadan.api.dto.familyCostumer.model.FamilyCostumerSearchRequestDto;
import com.ramadan.api.dto.familyCostumer.model.FamilyCostumerUpdateDto;
import com.ramadan.api.entity.costumer.FamilyCostumer;
import com.ramadan.api.exceptions.APIErrorException;

public interface IFamilyCostumerService {

	FamilyCostumerDto getFamilyCostumerByUuid(String uuid) throws APIErrorException;

	List<FamilyCostumerResponseDto> getAllFamilyCostumer() throws APIErrorException;

	FamilyCostumerResponseDto saveFamilyCostumer(FamilyCostumerRequestDto familyCostumerRequestDto)
			throws APIErrorException;

	void deleteFamilyCostumer(String uuid) throws APIErrorException;

	FamilyCostumer checkFamilyCostumerUuid(String uuid) throws APIErrorException;

	Map<String, Object> findAllByParent(String uuid, int page, int size, String[] sort) throws APIErrorException;

	Map<String, Object> findAllByCompany(String uuid, int page, int size, String[] sort) throws APIErrorException;

	FamilyCostumerResponseDto updateFamilyCostumer(FamilyCostumerUpdateDto familyCostumerUpdate)
			throws APIErrorException;


	Map<String, Object> search(FamilyCostumerSearchRequestDto searchRequestDto, int page, int size, String[] sort)
			throws APIErrorException;
	
}
