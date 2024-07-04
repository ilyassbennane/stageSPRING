package com.ramadan.api.services.user.phone;

import java.util.List;
import java.util.Map;

import com.ramadan.api.dto.user.PhoneUser.PhoneUserRequestDto;
import com.ramadan.api.dto.user.PhoneUser.PhoneUserResponseDto;
import com.ramadan.api.dto.user.PhoneUser.PhoneUserUpdateDto;
import com.ramadan.api.entity.user.PhoneUser;
import com.ramadan.api.exceptions.APIErrorException;

public interface IPhoneUserService {
    PhoneUserResponseDto findByPhoneNumber(String phoneNumber) throws APIErrorException;

    PhoneUserResponseDto findByUuid(String uuid) throws APIErrorException;

    Map<String, Object> findAll(int page, int size) throws APIErrorException;

    PhoneUserResponseDto savePhoneUser(PhoneUserRequestDto phoneUserRequestDto) throws APIErrorException;

    void deletePhoneUser(String uuid) throws APIErrorException;

    PhoneUserResponseDto updatePhoneUser(String uuid, PhoneUserUpdateDto requestDto) throws APIErrorException;

    List<PhoneUserResponseDto> getAllByUser(String uuid) throws APIErrorException;
    
    PhoneUser checkTelephoneUuid(String uuid) throws APIErrorException;


}
