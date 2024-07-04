// Source code is decompiled from a .class file using FernFlower decompiler.
package com.ramadan.api.services.company;

import com.ramadan.api.dto.company.model.CompanyRequestDto;

import com.ramadan.api.dto.company.model.CompanyResponseDto;
import com.ramadan.api.dto.company.model.CompanyUpdateDto;
import com.ramadan.api.dto.company.service.ICompanyMapperService;
import com.ramadan.api.entity.company.Company;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ErrorCode;
import com.ramadan.api.helpers.Utils;
import com.ramadan.api.repository.company.CompanyRepository;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CompanyService implements ICompanyService {
   @Autowired
   private CompanyRepository companyRepository;
   @Autowired
   private ICompanyMapperService companyMapperService;
   private static final Logger log = LoggerFactory.getLogger(CompanyService.class);

   public CompanyService() {
   }

   public CompanyResponseDto createCompany(@Valid CompanyRequestDto companyRequestDto) throws APIErrorException {
      if (Objects.isNull(companyRequestDto)) {
         throw new APIErrorException(ErrorCode.A507);
      } else {
         Company oCompany = this.companyMapperService.convertRequestSaveDtoToEntity(companyRequestDto);
         Company oCompanyCreated = (Company)this.companyRepository.save(oCompany);
         log.info(String.format("Company %s has been created.", oCompanyCreated.getUuid()));
         CompanyResponseDto oCompanyDto = this.companyMapperService.convertEntityToDto(oCompanyCreated);
         return oCompanyDto;
      }
   }

   public Map<String, Object> getAllCompanies(String name, int page, int size, String[] sort) throws APIErrorException {
      List<Sort.Order> orders = Utils.getListOrderBySort(sort);
      new ArrayList();
      Pageable oPageable = PageRequest.of(page, size, Sort.by(orders));
      Page pCompanys;
      if (Objects.isNull(name)) {
         pCompanys = this.companyRepository.findAll(oPageable);
      } else {
         pCompanys = this.companyRepository.findByName(name, oPageable);
      }

      List<Company> lCompanys = pCompanys.getContent();
      List<CompanyResponseDto> lCompanyDto = this.companyMapperService.convertListToListDto(lCompanys);
      Map<String, Object> lCompanysMap = new HashMap();
      lCompanysMap.put("companies", lCompanyDto);
      lCompanysMap.put("currentPage", pCompanys.getNumber());
      lCompanysMap.put("totalItems", pCompanys.getTotalElements());
      lCompanysMap.put("totalPages", pCompanys.getTotalPages());
      return lCompanysMap;
   }

   public CompanyResponseDto getCompanyByUuid(String Uuid) throws APIErrorException {
      Company oCompany = this.checkCompanyUuid(Uuid);
      CompanyResponseDto oCompanyDto = this.companyMapperService.convertEntityToDto(oCompany);
      return oCompanyDto;
   }

   public void deleteCompany(String CompanyUuid) throws APIErrorException {
      Company oCompany = this.checkCompanyUuid(CompanyUuid);
      oCompany.setDelete(true);
      this.companyRepository.save(oCompany);
      log.info(String.format("Company %s has been deleted.", CompanyUuid));
   }

   public CompanyResponseDto updateCompany(CompanyUpdateDto companyUpdateRequestDto) throws APIErrorException {
      if (Objects.isNull(companyUpdateRequestDto)) {
         throw new APIErrorException(ErrorCode.A0063);
      } else {
         Company oCompany = this.checkCompanyUuid(companyUpdateRequestDto.getUuid());
         if (companyUpdateRequestDto.getName() != null && !companyUpdateRequestDto.getName().isEmpty()) {
            oCompany.setName(companyUpdateRequestDto.getName());
         }

         if (companyUpdateRequestDto.getCapital() != null && !companyUpdateRequestDto.getCapital().isEmpty()) {
            oCompany.setCapital(companyUpdateRequestDto.getCapital());
         }

         if (companyUpdateRequestDto.getCnss() != null && !companyUpdateRequestDto.getCnss().isEmpty()) {
            oCompany.setCnss(companyUpdateRequestDto.getCnss());
         }

         if (companyUpdateRequestDto.getFax() != null && !companyUpdateRequestDto.getFax().isEmpty()) {
            oCompany.setFax(companyUpdateRequestDto.getFax());
         }

         if (companyUpdateRequestDto.getCodeSecteurActivite() != null && !companyUpdateRequestDto.getCodeSecteurActivite().isEmpty()) {
            oCompany.setCodeSecteurActivite(companyUpdateRequestDto.getCodeSecteurActivite());
         }

         if (companyUpdateRequestDto.getCodeVille() != null && !companyUpdateRequestDto.getCodeVille().isEmpty()) {
            oCompany.setCodeVille(companyUpdateRequestDto.getCodeVille());
         }

         if (companyUpdateRequestDto.getWeb() != null && !companyUpdateRequestDto.getWeb().isEmpty()) {
            oCompany.setWeb(companyUpdateRequestDto.getWeb());
         }

         if (companyUpdateRequestDto.getTravauxPublic() != null && !companyUpdateRequestDto.getTravauxPublic().isEmpty()) {
            oCompany.setTravauxPublic(companyUpdateRequestDto.getTravauxPublic());
         }

         if (companyUpdateRequestDto.getIdentifiantFiscal() != null && !companyUpdateRequestDto.getIdentifiantFiscal().isEmpty()) {
            oCompany.setIdentifiantFiscal(companyUpdateRequestDto.getIdentifiantFiscal());
         }

         if (companyUpdateRequestDto.getRegistreCommercial() != null && !companyUpdateRequestDto.getRegistreCommercial().isEmpty()) {
            oCompany.setRegistreCommercial(companyUpdateRequestDto.getRegistreCommercial());
         }

         if (companyUpdateRequestDto.getPattente() != null && !companyUpdateRequestDto.getPattente().isEmpty()) {
            oCompany.setPattente(companyUpdateRequestDto.getPattente());
         }

         if (companyUpdateRequestDto.getTelephone() != null && !companyUpdateRequestDto.getTelephone().isEmpty()) {
            oCompany.setTelephone(companyUpdateRequestDto.getTelephone());
         }

         if (companyUpdateRequestDto.getPattente() != null && !companyUpdateRequestDto.getPattente().isEmpty()) {
            oCompany.setPattente(companyUpdateRequestDto.getPattente());
         }

         if (companyUpdateRequestDto.getDescription() != null && !companyUpdateRequestDto.getDescription().isEmpty()) {
            oCompany.setDescription(companyUpdateRequestDto.getDescription());
         }

         if (companyUpdateRequestDto.getPrefix() != null && !companyUpdateRequestDto.getPrefix().isEmpty()) {
            oCompany.setPrefix(companyUpdateRequestDto.getPrefix());
         }

         Company oCompanyUpdated = (Company)this.companyRepository.save(oCompany);
         log.info(String.format("Company %s has been updated.", oCompanyUpdated.getUuid()));
         CompanyResponseDto oCompanyDto = this.companyMapperService.convertEntityToDto(oCompanyUpdated);
         return oCompanyDto;
      }
   }

   public Company checkCompanyUuid(String uuid) throws APIErrorException {
      if (uuid == null) {
         throw new APIErrorException(ErrorCode.A0064);
      } else {
         Company oCompany = this.companyRepository.findByUuid(uuid);
         if (Objects.isNull(oCompany)) {
            throw new APIErrorException(ErrorCode.A0061);
         } else {
            return oCompany;
         }
      }
   }
}
