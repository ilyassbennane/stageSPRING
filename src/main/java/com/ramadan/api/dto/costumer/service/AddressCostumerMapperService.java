package com.ramadan.api.dto.costumer.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.ramadan.api.dto.costumer.model.AddressCostumerRequestDto;
import com.ramadan.api.dto.costumer.model.AddressCostumerResponseDto;
import com.ramadan.api.entity.costumer.AddressCostumer;
import com.ramadan.api.entity.costumer.Costumer;


@Service
public class AddressCostumerMapperService implements IAddressCostumerMapperService{

    private final ModelMapper modelMapper;
    
    public AddressCostumerMapperService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    @Override
    public Page<AddressCostumerResponseDto> convertToResponseDto(Page<AddressCostumer> addresses) {
        return addresses.map((element) -> modelMapper.map(element, AddressCostumerResponseDto.class));
    }

    @Override
    public AddressCostumer convertRequestSaveDtoToEntity(AddressCostumerRequestDto AdresseCostumerDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return modelMapper.map(AdresseCostumerDto, AddressCostumer.class);
    }

    @Override
    public AddressCostumer convertDtoToEntity(AddressCostumerResponseDto oAdresseCostumerDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return modelMapper.map(oAdresseCostumerDto, AddressCostumer.class);
    }

    @Override
    public AddressCostumerResponseDto convertEntityToDto(AddressCostumer oAdresseCostumer) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return modelMapper.map(oAdresseCostumer, AddressCostumerResponseDto.class);
    }

    @Override
    public List<AddressCostumerResponseDto> convertListToListDto(Collection<AddressCostumer> entityList) {
        return entityList == null ? Collections.emptyList() : entityList.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }
    @Override
    public List<AddressCostumer> mapAddresses(List<AddressCostumerRequestDto> adresseCostumerDtos, Costumer customer) {
        return adresseCostumerDtos.stream()
                .map(addressDto -> {
                    AddressCostumer address = new AddressCostumer();
                    address.setCostumer(customer); 
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
