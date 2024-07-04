package com.ramadan.api.services.costumer;

import java.util.List;
import java.util.Map;

import com.ramadan.api.dto.costumer.model.AddressCostumerRequestDto;
import com.ramadan.api.dto.costumer.model.AddressCostumerResponseDto;
import com.ramadan.api.dto.costumer.model.AddressCostumerUpdateListDto;
import com.ramadan.api.dto.costumer.model.CostumerDto;
import com.ramadan.api.dto.costumer.model.CostumerFilterRequestDto;
import com.ramadan.api.dto.costumer.model.CostumerRequestDto;
import com.ramadan.api.dto.costumer.model.CostumerUpdateDto;
import com.ramadan.api.dto.costumer.model.EmailCostumerRequestDto;
import com.ramadan.api.dto.costumer.model.EmailCostumerResponseDto;
import com.ramadan.api.dto.costumer.model.EmailCostumerUpdateListDto;
import com.ramadan.api.dto.costumer.model.PhoneCostumerRequestDto;
import com.ramadan.api.dto.costumer.model.PhoneCostumerResponseDto;
import com.ramadan.api.dto.costumer.model.PhoneCostumerUpdateListDto;
import com.ramadan.api.entity.costumer.Costumer;
import com.ramadan.api.exceptions.APIErrorException;

public interface ICostumerService {

	CostumerDto findByUuid(String uuid) throws APIErrorException;

	Costumer checkCostumerUuid(String uuid) throws APIErrorException;

	CostumerDto updateEntity(String uuid, CostumerUpdateDto requestDto) throws APIErrorException;

	void deleteCostumer(String uuid) throws APIErrorException;

	CostumerDto addAddressToCostumer(String costumerUuid, AddressCostumerRequestDto addressDto)
			throws APIErrorException;

	CostumerDto setMainAddress(String addressCostumerUuid) throws APIErrorException;

	List<AddressCostumerResponseDto> getAllAddressCostumers(String customerUuid) throws APIErrorException;

	CostumerDto addPhoneToCostumer(String costumerUuid, PhoneCostumerRequestDto phoneDto) throws APIErrorException;

	CostumerDto setMainPhone(String phoneCostumerUuid) throws APIErrorException;

	List<PhoneCostumerResponseDto> getAllPhonesCostumer(String customerUuid) throws APIErrorException;

	CostumerDto addEmailToCostumer(String costumerUuid, EmailCostumerRequestDto emailDto) throws APIErrorException;

	CostumerDto setMainEmail(String emailCostumerUuid) throws APIErrorException;

	List<EmailCostumerResponseDto> getAllEmailsCostumer(String customerUuid) throws APIErrorException;


	List<CostumerDto> saveCostumer(List<CostumerRequestDto> customerRequestDtos) throws APIErrorException;

	void updatePhonesForCostumer(PhoneCostumerUpdateListDto phoneUpdateListDto) throws APIErrorException;

	void updateAddressesForCostumer(AddressCostumerUpdateListDto addressUpdateListDto) throws APIErrorException;

	void updateEmailsForCostumer(EmailCostumerUpdateListDto emailUpdateListDto) throws APIErrorException;
	
	void deletePhonesForCostumer(List<String> phoneUuids) throws APIErrorException;
	
	void deleteAddressesForCostumer(List<String> addressUuids) throws APIErrorException;
	
	void deleteEmailsForCostumer(List<String> emailUuids) throws APIErrorException;

	Map<String, Object> search(CostumerFilterRequestDto searchRequestDto, int page, int size, String[] sort)
			throws APIErrorException;



}
