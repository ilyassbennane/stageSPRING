// Source code is decompiled from a .class file using FernFlower decompiler.
package com.ramadan.api.dto.company.service;

import com.ramadan.api.dto.company.model.CompanyRequestDto;
import com.ramadan.api.dto.company.model.CompanyResponseDto;
import com.ramadan.api.dto.company.model.CompanyUpdateDto;
import com.ramadan.api.entity.company.Company;
import java.util.Collection;
import java.util.List;

public interface ICompanyMapperService {
   Company convertRequestSaveDtoToEntity(CompanyRequestDto CompanyDto);

   Company convertRequestUpdateDtoToEntity(CompanyUpdateDto CompanyDto);

   Company convertDtoToEntity(CompanyResponseDto oCompanyDto);

   CompanyResponseDto convertEntityToDto(Company oCompany);

   List<CompanyResponseDto> convertListToListDto(final Collection<Company> entityList);
}
