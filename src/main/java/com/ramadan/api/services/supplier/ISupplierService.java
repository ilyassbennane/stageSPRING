package com.ramadan.api.services.supplier;

import java.util.Map;

import com.ramadan.api.dto.supplier.SupplierRequestDto;
import com.ramadan.api.dto.supplier.SupplierResponseDto;
import com.ramadan.api.dto.supplier.SupplierUpdateDto;
import com.ramadan.api.exceptions.APIErrorException;

public interface ISupplierService {
    SupplierResponseDto findByUuid(String uuid) throws APIErrorException;

    Map<String, Object> findAllByCompany(String companyUuid, int page, int size, String[] sort) throws APIErrorException;

    SupplierResponseDto saveSupplier(SupplierRequestDto supplierRequestDto) throws APIErrorException;

    SupplierResponseDto updateSupplier(SupplierUpdateDto supplierRequestDto) throws APIErrorException;

    void deleteSupplier(String uuid) throws APIErrorException;

    Map<String, Object> getAll(int page, int size, String[] sort) throws APIErrorException;
}
