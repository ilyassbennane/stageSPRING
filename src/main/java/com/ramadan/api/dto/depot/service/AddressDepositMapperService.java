package com.ramadan.api.dto.depot.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.ramadan.api.dto.depot.model.AddressDepositRequestDto;
import com.ramadan.api.dto.depot.model.AddressDepositResponseDto;
import com.ramadan.api.entity.stock.deposit.AddressDeposit;
import com.ramadan.api.entity.stock.deposit.Deposit;



@Service

public class AddressDepositMapperService implements IAddressDepositMapperService{


    private final ModelMapper modelMapper;
    public AddressDepositMapperService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    @Override
    public Page<AddressDepositResponseDto> convertToResponseDto(Page<AddressDeposit> addresses) {
        return addresses.map((element) -> modelMapper.map(element, AddressDepositResponseDto.class));
    }

    @Override
    public AddressDeposit convertRequestSaveDtoToEntity(AddressDepositRequestDto AdresseDepositDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return modelMapper.map(AdresseDepositDto, AddressDeposit.class);
    }

    @Override
    public AddressDeposit convertDtoToEntity(AddressDepositResponseDto oAdresseDepositDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return modelMapper.map(oAdresseDepositDto, AddressDeposit.class);
    }

    @Override
    public AddressDepositResponseDto convertEntityToDto(AddressDeposit oAdresseDeposit) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return modelMapper.map(oAdresseDeposit, AddressDepositResponseDto.class);
    }

    @Override
    public List<AddressDepositResponseDto> convertListToListDto(Collection<AddressDeposit> entityList) {
        return entityList == null ? Collections.emptyList() : entityList.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }
    @Override
    public List<AddressDeposit> mapAddresses(List<AddressDepositRequestDto> adresseDepositDtos, Deposit depot) {
        return adresseDepositDtos.stream()
                .map(addressDto -> {
                	AddressDeposit address = new AddressDeposit();
                    address.setDeposit(depot); 
                    address.setCodeVille(addressDto.getCodeVille());
                    address.setAdresse1(addressDto.getAddress1());
                    address.setAdresse2(addressDto.getAddress2());
                    address.setAdresse3(addressDto.getAddress3());
                  //  address.setMain(addressDto.isMain());
                    return address;
                })
                .collect(Collectors.toList());
    }
    
}