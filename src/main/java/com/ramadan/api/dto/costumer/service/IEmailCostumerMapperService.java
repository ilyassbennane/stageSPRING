package com.ramadan.api.dto.costumer.service;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;

import com.ramadan.api.dto.costumer.model.EmailCostumerRequestDto;
import com.ramadan.api.dto.costumer.model.EmailCostumerResponseDto;
import com.ramadan.api.entity.costumer.Costumer;
import com.ramadan.api.entity.costumer.EmailCostumer;

public interface IEmailCostumerMapperService {

	Page<EmailCostumerResponseDto> convertToResponseDto(Page<EmailCostumer> emails);

	EmailCostumer convertRequestSaveDtoToEntity(EmailCostumerRequestDto emailCostumerDto);

	EmailCostumer convertDtoToEntity(EmailCostumerResponseDto emailCostumerDto);

	EmailCostumerResponseDto convertEntityToDto(EmailCostumer emailCostumer);

	List<EmailCostumerResponseDto> convertListToListDto(Collection<EmailCostumer> entityList);

	List<EmailCostumer> mapEmails(List<EmailCostumerRequestDto> emailCostumerDtos, Costumer customer);

}
