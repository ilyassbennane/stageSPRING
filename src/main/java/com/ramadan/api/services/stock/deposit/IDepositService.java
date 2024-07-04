package com.ramadan.api.services.stock.deposit;

import com.ramadan.api.dto.depot.model.DepotRequestDto;
import com.ramadan.api.dto.depot.model.DepotResponseDto;
import com.ramadan.api.exceptions.APIErrorException;

public interface IDepositService {

	DepotResponseDto createDeposit(DepotRequestDto depotRequestDto) throws APIErrorException;

}
