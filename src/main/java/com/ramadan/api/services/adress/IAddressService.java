package com.ramadan.api.services.adress;

import java.util.List;

import java.util.Map;

import com.ramadan.api.dto.address.model.AddressCompanyRequestDto;
import com.ramadan.api.dto.address.model.AddressDepositRequestDto;
import com.ramadan.api.dto.address.model.AddressDto;
import com.ramadan.api.dto.address.model.AddressUpdateDto;
import com.ramadan.api.dto.agance.model.AddressAgencyRequestDto;
import com.ramadan.api.exceptions.APIErrorException;

public interface  IAddressService {
     AddressDto findByUuid(String uuid) throws APIErrorException;

    AddressDto saveAddressCompany(AddressCompanyRequestDto addressRequestDto) throws APIErrorException;

    //AddressDto saveAddressAgency(AddressAgencyRequestDto addressRequestDto) throws APIErrorException;

//    AddressDto saveAddressDeposit(AddressDepositRequestDto addressRequestDto) throws APIErrorException;

  //  AddressDto saveAddressCostumer(AddressCostumerRequestDto addressRequestDto) throws APIErrorException;

    AddressDto updateAddress(String uuid, AddressUpdateDto addressRequestDto) throws APIErrorException;

    List<AddressDto> findAllByCompany(String uuid) throws APIErrorException;

    List<AddressDto> findAllByCostumer(String uuid) throws APIErrorException;

    List<AddressDto> findAllByAgency(String uuid) throws APIErrorException;

    List<AddressDto> findAllByDeposit(String uuid) throws APIErrorException;

    Map<String, Object> findAll(int page, int size, String[] sort) throws APIErrorException;

    void deleteAddress(String uuid) throws APIErrorException;
}
