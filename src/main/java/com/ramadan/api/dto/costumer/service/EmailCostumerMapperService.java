package com.ramadan.api.dto.costumer.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.ramadan.api.dto.costumer.model.EmailCostumerRequestDto;
import com.ramadan.api.dto.costumer.model.EmailCostumerResponseDto;
import com.ramadan.api.entity.costumer.EmailCostumer;
import com.ramadan.api.entity.costumer.Costumer;

@Service
public class EmailCostumerMapperService implements IEmailCostumerMapperService {

    private final ModelMapper modelMapper;

    public EmailCostumerMapperService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<EmailCostumerResponseDto> convertToResponseDto(Page<EmailCostumer> emails) {
        return emails.map(email -> modelMapper.map(email, EmailCostumerResponseDto.class));
    }

    @Override
    public EmailCostumer convertRequestSaveDtoToEntity(EmailCostumerRequestDto emailCostumerDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return modelMapper.map(emailCostumerDto, EmailCostumer.class);
    }

    @Override
    public EmailCostumer convertDtoToEntity(EmailCostumerResponseDto emailCostumerDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return modelMapper.map(emailCostumerDto, EmailCostumer.class);
    }

    @Override
    public EmailCostumerResponseDto convertEntityToDto(EmailCostumer emailCostumer) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return modelMapper.map(emailCostumer, EmailCostumerResponseDto.class);
    }

    @Override
    public List<EmailCostumerResponseDto> convertListToListDto(Collection<EmailCostumer> entityList) {
        return entityList == null ? Collections.emptyList() : entityList.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmailCostumer> mapEmails(List<EmailCostumerRequestDto> emailCostumerDtos, Costumer customer) {
        return emailCostumerDtos.stream()
                .map(emailDto -> {
                    EmailCostumer email = new EmailCostumer();
                    email.setCostumer(customer);
                    email.setEmail(emailDto.getEmail());
                    return email;
                })
                .collect(Collectors.toList());
    }
}
