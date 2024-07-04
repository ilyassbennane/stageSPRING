// Source code is decompiled from a .class file using FernFlower decompiler.
package com.ramadan.api.services.company;

import com.ramadan.api.dto.company.model.CompanyRequestDto;
import com.ramadan.api.dto.company.model.CompanyResponseDto;
import com.ramadan.api.dto.company.model.CompanyUpdateDto;
import com.ramadan.api.entity.company.Company;
import com.ramadan.api.exceptions.APIErrorException;
import java.util.Map;

public interface ICompanyService {
   CompanyResponseDto createCompany(CompanyRequestDto companyRequestDto) throws APIErrorException;

   CompanyResponseDto getCompanyByUuid(String uuid) throws APIErrorException;

   void deleteCompany(String uuid) throws APIErrorException;

   CompanyResponseDto updateCompany(CompanyUpdateDto updateDto) throws APIErrorException;

   Map<String, Object> getAllCompanies(String name, int page, int size, String[] sort) throws APIErrorException;

   Company checkCompanyUuid(String uuid) throws APIErrorException;
}
