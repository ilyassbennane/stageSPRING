// Source code is decompiled from a .class file using FernFlower decompiler.
package com.ramadan.api.dto.company.service;

import com.ramadan.api.dto.company.model.CompanyRequestDto;
import com.ramadan.api.dto.company.model.CompanyResponseDto;
import com.ramadan.api.dto.company.model.CompanyUpdateDto;
import com.ramadan.api.entity.company.Company;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyMapperService implements ICompanyMapperService {
   @Autowired
   private ModelMapper modelMapper;

   public CompanyMapperService() {
   }

   public Company convertDtoToEntity(CompanyResponseDto oCompanyDto) {
      this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
      Company Company = (Company)this.modelMapper.map(oCompanyDto, Company.class);
      return Company;
   }

   public CompanyResponseDto convertEntityToDto(Company oCompany) {
      this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
      CompanyResponseDto CompanyDto = (CompanyResponseDto)this.modelMapper.map(oCompany, CompanyResponseDto.class);
      return CompanyDto;
   }

   public Company convertRequestUpdateDtoToEntity(CompanyUpdateDto CompanyDto) {
      this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
      Company Company = (Company)this.modelMapper.map(CompanyDto, Company.class);
      return Company;
   }

   public List<CompanyResponseDto> convertListToListDto(final Collection<Company> entityList) {
      return entityList == null ? Collections.emptyList() : (List<CompanyResponseDto>)entityList.stream().map((entity) -> {
         return this.convertEntityToDto(entity);
      }).collect(Collectors.toList());
   }

   public Company convertRequestSaveDtoToEntity(CompanyRequestDto CompanyDto) {
      this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
      Company Company = (Company)this.modelMapper.map(CompanyDto, Company.class);
      return Company;
   }
}
