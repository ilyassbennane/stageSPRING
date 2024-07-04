package com.ramadan.api.dto.depot.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.ramadan.api.dto.depot.model.DepotResponseDto;
import com.ramadan.api.entity.stock.deposit.Deposit;

@Service

public class DepoMapperService implements IDepoMapperService {

	private final ModelMapper modelMapper;

	public DepoMapperService(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Override
	public Page<DepotResponseDto> convertToResponseDto(Page<Deposit> depots) {
		return depots.map((element) -> modelMapper.map(element, DepotResponseDto.class));
	}




}
