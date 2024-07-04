package com.ramadan.api.dto.depot.service;

import org.springframework.data.domain.Page;


import com.ramadan.api.dto.depot.model.DepotResponseDto;
import com.ramadan.api.entity.stock.deposit.Deposit;

public interface IDepotMapperService {
    Page<DepotResponseDto> convertToResponseDto(Page<Deposit> depots);
}
