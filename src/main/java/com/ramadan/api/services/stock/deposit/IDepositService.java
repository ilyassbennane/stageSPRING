package com.ramadan.api.services.stock.deposit;

import java.util.List;

import com.ramadan.api.dto.depot.model.AddressDepositRequestDto;
import com.ramadan.api.dto.depot.model.AddressDepositResponseDto;
import com.ramadan.api.dto.depot.model.DepotRequestDto;
import com.ramadan.api.dto.depot.model.DepotResponseDto;
import com.ramadan.api.dto.depot.model.DepotUpdateDto;
import com.ramadan.api.entity.stock.deposit.Deposit;
import com.ramadan.api.exceptions.APIErrorException;

public interface IDepositService {

	DepotResponseDto createDeposit(DepotRequestDto depotRequestDto) throws APIErrorException;

	DepotResponseDto addAddressToDepot(String depotUuid, AddressDepositRequestDto addressDto) throws APIErrorException;

	Deposit checkDepositUuid(String uuid) throws APIErrorException;

	DepotResponseDto setMainAddress(String addressDepositUuid) throws APIErrorException;

	List<AddressDepositResponseDto> getAllAddressDepot(String depotUuid) throws APIErrorException;

	DepotResponseDto updateEntity(String uuid, DepotUpdateDto requestDto) throws APIErrorException;

	void deleteDeposit(String uuid) throws APIErrorException;

}
