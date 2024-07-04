package com.ramadan.api.dto.costumer.service;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;

import com.ramadan.api.dto.costumer.model.AddressCostumerRequestDto;
import com.ramadan.api.dto.costumer.model.AddressCostumerResponseDto;
import com.ramadan.api.entity.costumer.AddressCostumer;
import com.ramadan.api.entity.costumer.Costumer;

public interface IAddressCostumerMapperService {

	Page<AddressCostumerResponseDto> convertToResponseDto(Page<AddressCostumer> addresses);

	AddressCostumer convertRequestSaveDtoToEntity(AddressCostumerRequestDto AdresseCostumerDto);

	AddressCostumer convertDtoToEntity(AddressCostumerResponseDto oAdresseCostumerDto);

	AddressCostumerResponseDto convertEntityToDto(AddressCostumer oAdresseCostumer);

	List<AddressCostumerResponseDto> convertListToListDto(Collection<AddressCostumer> entityList);

	List<AddressCostumer> mapAddresses(List<AddressCostumerRequestDto> adresseCostumerDtos, Costumer customer);

}
