package com.ramadan.api.dto.costumer.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.ramadan.api.dto.costumer.model.PhoneCostumerRequestDto;
import com.ramadan.api.dto.costumer.model.PhoneCostumerResponseDto;
import com.ramadan.api.entity.costumer.PhoneCostumer;
import com.ramadan.api.entity.costumer.Costumer;

@Service
public class PhoneCostumerMapperService implements IPhoneCostumerMapperService {

    private final ModelMapper modelMapper;

    public PhoneCostumerMapperService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<PhoneCostumerResponseDto> convertToResponseDto(Page<PhoneCostumer> phones) {
        return phones.map(phone -> modelMapper.map(phone, PhoneCostumerResponseDto.class));
    }

    @Override
    public PhoneCostumer convertRequestSaveDtoToEntity(PhoneCostumerRequestDto phoneCostumerDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return modelMapper.map(phoneCostumerDto, PhoneCostumer.class);
    }

    @Override
    public PhoneCostumer convertDtoToEntity(PhoneCostumerResponseDto phoneCostumerDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return modelMapper.map(phoneCostumerDto, PhoneCostumer.class);
    }

    @Override
    public PhoneCostumerResponseDto convertEntityToDto(PhoneCostumer phoneCostumer) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return modelMapper.map(phoneCostumer, PhoneCostumerResponseDto.class);
    }

    @Override
    public List<PhoneCostumerResponseDto> convertListToListDto(Collection<PhoneCostumer> entityList) {
        return entityList == null ? Collections.emptyList() : entityList.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PhoneCostumer> mapPhones(List<PhoneCostumerRequestDto> phoneCostumerDtos, Costumer customer) {
        return phoneCostumerDtos.stream()
                .map(phoneDto -> {
                    PhoneCostumer phone = new PhoneCostumer();
                    phone.setCostumer(customer);
                    phone.setCodeTypePhone(phoneDto.getCodeTypePhone());
                    phone.setPrefix(phoneDto.getPrefix());
                    phone.setNumbre(phoneDto.getNumbre());
                    return phone;
                })
                .collect(Collectors.toList());
    }
}
