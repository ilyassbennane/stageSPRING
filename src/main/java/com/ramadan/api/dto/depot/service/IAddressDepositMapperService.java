package com.ramadan.api.dto.depot.service;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;

import com.ramadan.api.dto.depot.model.AddressDepositRequestDto;
import com.ramadan.api.dto.depot.model.AddressDepositResponseDto;
import com.ramadan.api.entity.stock.deposit.AddressDeposit;
import com.ramadan.api.entity.stock.deposit.Deposit;

public interface IAddressDepositMapperService {

	Page<AddressDepositResponseDto> convertToResponseDto(Page<AddressDeposit> addresses);

	AddressDeposit convertRequestSaveDtoToEntity(AddressDepositRequestDto AdresseDepositDto);

	AddressDeposit convertDtoToEntity(AddressDepositResponseDto oAdresseDepositDto);

	AddressDepositResponseDto convertEntityToDto(AddressDeposit oAdresseDeposit);

	List<AddressDepositResponseDto> convertListToListDto(Collection<AddressDeposit> entityList);

	List<AddressDeposit> mapAddresses(List<AddressDepositRequestDto> adresseDepositDtos, Deposit depot);

}
