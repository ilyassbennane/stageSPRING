package com.ramadan.api.dto.agance.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.ramadan.api.dto.agance.model.PhoneAgencyRequestDto;
import com.ramadan.api.dto.agance.model.PhoneAgencyResponseDto;
import com.ramadan.api.dto.costumer.model.PhoneCostumerRequestDto;
import com.ramadan.api.dto.costumer.model.PhoneCostumerResponseDto;
import com.ramadan.api.entity.agence.Agency;
import com.ramadan.api.entity.agence.PhoneAgency;


@Service
public class PhoneAgencyMapperService implements IPhoneAgencyMapperService{
    private final ModelMapper modelMapper;

    public PhoneAgencyMapperService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<PhoneAgencyResponseDto> convertToResponseDto(Page<PhoneAgency> phones) {
        return phones.map(phone -> modelMapper.map(phone, PhoneAgencyResponseDto.class));
    }

    @Override
    public PhoneAgency convertRequestSaveDtoToEntity(PhoneAgencyRequestDto phoneAgencyDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return modelMapper.map(phoneAgencyDto, PhoneAgency.class);
    }

    @Override
    public PhoneAgency convertDtoToEntity(PhoneAgencyResponseDto phoneAgencyDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return modelMapper.map(phoneAgencyDto, PhoneAgency.class);
    }

    @Override
    public PhoneAgencyResponseDto convertEntityToDto(PhoneAgency phoneAgency) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return modelMapper.map(phoneAgency, PhoneAgencyResponseDto.class);
    }

    @Override
    public List<PhoneAgencyResponseDto> convertListToListDto(Collection<PhoneAgency> entityList) {
        return entityList == null ? Collections.emptyList() : entityList.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PhoneAgency> mapPhones(List<PhoneAgencyRequestDto> phoneAgencyDtos, Agency agency) {
        return phoneAgencyDtos.stream()
                .map(phoneDto -> {
                	PhoneAgency phone = new PhoneAgency();
                    phone.setAgency(agency);
                    phone.setCodeTypePhone(phoneDto.getCodeTypePhone());
                    phone.setPrefix(phoneDto.getPrefix());
                    phone.setNumbre(phoneDto.getNumbre());
                    return phone;
                })
                .collect(Collectors.toList());
    }
}
