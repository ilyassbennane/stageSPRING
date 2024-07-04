package com.ramadan.api.services.supplier;

import com.ramadan.api.dto.MapClassWithDto;
import com.ramadan.api.dto.supplier.SupplierRequestDto;
import com.ramadan.api.dto.supplier.SupplierResponseDto;
import com.ramadan.api.dto.supplier.SupplierUpdateDto;
import com.ramadan.api.entity.company.Company;
import com.ramadan.api.entity.company.Supplier;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ErrorCode;
import com.ramadan.api.helpers.Utils;
import com.ramadan.api.repository.company.CompanyRepository;
import com.ramadan.api.repository.company.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class SupplierService implements ISupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private MapClassWithDto<Supplier, SupplierRequestDto> mapClassWithRequestDto;

    @Autowired
    private MapClassWithDto<Supplier, SupplierResponseDto> mapClassWithResponseDto;

    @Override
    public SupplierResponseDto findByUuid(String uuid) throws APIErrorException {
        Supplier supplier = supplierRepository.findByUuid(uuid);
        if (supplier == null) {
            throw new APIErrorException(ErrorCode.S001);
        } else {
            return mapClassWithResponseDto.convertToDto(supplier, SupplierResponseDto.class);
        }
    }

    @Override
    public Map<String, Object> findAllByCompany(String companyUuid, int page, int size, String[] sort) throws APIErrorException {
        Company company = companyRepository.findByUuid(companyUuid);
        if (company != null) {
            List<Sort.Order> orders = Utils.getListOrderBySort(sort);
            Pageable pageable = PageRequest.of(page, size, Sort.by(orders));

            Page<Supplier> supplierPage = supplierRepository.findAllByCompany(company, pageable);
            if (supplierPage.hasContent()) {
                List<SupplierResponseDto> suppliers = supplierPage.getContent().stream()
                        .map(supplier -> mapClassWithResponseDto.convertToDto(supplier, SupplierResponseDto.class))
                        .collect(Collectors.toList());

                Map<String, Object> response = new HashMap<>();
                response.put("suppliers", suppliers);
                response.put("currentPage", supplierPage.getNumber());
                response.put("totalItems", supplierPage.getTotalElements());
                response.put("totalPages", supplierPage.getTotalPages());

                return response;
            } else {
                throw new APIErrorException(ErrorCode.S003);
            }
        } else {
            throw new APIErrorException(ErrorCode.A010);
        }
    }

    @Override
    public SupplierResponseDto saveSupplier(SupplierRequestDto supplierRequestDto) throws APIErrorException {
        if (supplierRequestDto != null) {
            Company company = companyRepository.findByUuid(supplierRequestDto.getCompanyUuid());
            if (company == null) {
                throw new APIErrorException(ErrorCode.A010);
            } else {
                Supplier supplier = mapClassWithRequestDto.convertToEntity(supplierRequestDto, Supplier.class);
                supplier.setCompany(company);
                Supplier savedSupplier = supplierRepository.save(supplier);
                return mapClassWithResponseDto.convertToDto(savedSupplier, SupplierResponseDto.class);
            }
        } else {
            throw new APIErrorException(ErrorCode.A507);
        }
    }

    // Implement updateSupplier method similarly to the saveSupplier method.

    @Override
    public void deleteSupplier(String uuid) throws APIErrorException {
        Supplier supplier = supplierRepository.findByUuid(uuid);
        if (supplier != null) {
            supplier.setDelete(true);
            supplierRepository.save(supplier);
        } else {
            throw new APIErrorException(ErrorCode.S001);
        }
    }

    @Override
public Map<String, Object> getAll(int page, int size, String[] sort) throws APIErrorException {
    List<Sort.Order> orders = Utils.getListOrderBySort(sort);
    Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
    Page<Supplier> supplierPage = supplierRepository.findAll(pageable);

    List<SupplierResponseDto> suppliers = supplierPage.getContent().stream()
            .map(supplier -> mapClassWithResponseDto.convertToDto(supplier, SupplierResponseDto.class))
            .collect(Collectors.toList());

    Map<String, Object> response = new HashMap<>();
    response.put("suppliers", suppliers);
    response.put("currentPage", supplierPage.getNumber());
    response.put("totalItems", supplierPage.getTotalElements());
    response.put("totalPages", supplierPage.getTotalPages());

    return response;
}

@Override
public SupplierResponseDto updateSupplier(SupplierUpdateDto requestDto) throws APIErrorException {
    Supplier existingSupplier = supplierRepository.findByUuid(requestDto.getUuid());
    if (existingSupplier == null) {
        throw new APIErrorException(ErrorCode.A507);
    } else {
        // Update supplier attributes if provided in the requestDto
        if (requestDto.getCodeTypeFournisseur() != null) {
            existingSupplier.setCodeTypeFournisseur(requestDto.getCodeTypeFournisseur());
        }
        if (requestDto.getBarcode() != null) {
            existingSupplier.setBarcode(requestDto.getBarcode());
        }
        if (requestDto.getTelephone() != null) {
            existingSupplier.setTelephone(requestDto.getTelephone());
        }
        if (requestDto.getEmail() != null) {
            existingSupplier.setEmail(requestDto.getEmail());
        }
        if (requestDto.getRaisonSociale1() != null) {
            existingSupplier.setRaisonSociale1(requestDto.getRaisonSociale1());
        }
        if (requestDto.getRaisonSociale2() != null) {
            existingSupplier.setRaisonSociale2(requestDto.getRaisonSociale2());
        }
        if (requestDto.getRang() != null) {
            existingSupplier.setRang(requestDto.getRang());
        }
        // Add similar update logic for other attributes

        Supplier updatedSupplier = supplierRepository.save(existingSupplier);
        return mapClassWithResponseDto.convertToDto(updatedSupplier, SupplierResponseDto.class);
    }
}


}
