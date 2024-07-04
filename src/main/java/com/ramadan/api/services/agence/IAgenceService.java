package com.ramadan.api.services.agence;

import java.util.List;
import java.util.Map;

import com.ramadan.api.dto.agance.model.AddressAgencyRequestDto;
import com.ramadan.api.dto.agance.model.AddressAgencyResponseDto;
import com.ramadan.api.dto.agance.model.AddressAgencyUpdateListDto;
import com.ramadan.api.dto.agance.model.AgenceFilterRequestDto;
import com.ramadan.api.dto.agance.model.AgenceRequestDto;
import com.ramadan.api.dto.agance.model.AgenceResponseDto;
import com.ramadan.api.dto.agance.model.AgenceUpdateDto;
import com.ramadan.api.dto.agance.model.PhoneAgencyRequestDto;
import com.ramadan.api.dto.agance.model.PhoneAgencyResponseDto;
import com.ramadan.api.dto.agance.model.PhoneAgencyUpdateListDto;
import com.ramadan.api.dto.user.model.UserKeycloak;
import com.ramadan.api.entity.agence.Agency;
import com.ramadan.api.entity.user.User;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ApiKeyException;

public interface IAgenceService {

	AgenceResponseDto findByUuid(String uuid) throws APIErrorException;

	Map<String, Object> findAllByCompany(String uuid, int page, int size, String[] sort) throws APIErrorException;

	AgenceResponseDto saveAgence(AgenceRequestDto agenceRequestDto) throws APIErrorException;

	AgenceResponseDto updateEntity(String uuid, AgenceUpdateDto requestDto) throws ApiKeyException;

	void deleteAgence(String uuid) throws APIErrorException;


	Agency checkAgenceUuid(String uuid) throws APIErrorException;

//	UserKeycloak getUser(String token) throws APIErrorException;
//
//	User getUserConnecter() throws APIErrorException;

	AgenceResponseDto addAddressToAgence(String agencyUuid, AddressAgencyRequestDto addressDto)
			throws APIErrorException;

	AgenceResponseDto setMainAddress(String addressAgencyUuid) throws APIErrorException;

	List<AddressAgencyResponseDto> getAllAddressAgencies(String agencyUuid) throws APIErrorException;

	AgenceResponseDto addPhoneToAgency(String agencyUUId, PhoneAgencyRequestDto phoneDto) throws APIErrorException;

	AgenceResponseDto setMainPhone(String phoneAgencyUuid) throws APIErrorException;

	List<PhoneAgencyResponseDto> getAllPhonesAgency(String agencyUuid) throws APIErrorException;

	void updatePhonesForAgency(PhoneAgencyUpdateListDto phoneUpdateListDto) throws APIErrorException;

	void updateAddressesForAgency(AddressAgencyUpdateListDto addressUpdateListDto) throws APIErrorException;

	Map<String, Object> search(AgenceFilterRequestDto searchRequestDto, int page, int size, String[] sort)
			throws APIErrorException;
}
