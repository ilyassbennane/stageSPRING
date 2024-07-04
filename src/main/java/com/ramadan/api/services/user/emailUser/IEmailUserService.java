package com.ramadan.api.services.user.emailUser;

import java.util.List;
import java.util.Map;

import com.ramadan.api.dto.user.email.EmailRequestDto;
import com.ramadan.api.dto.user.email.EmailResponseDto;
import com.ramadan.api.dto.user.email.EmailUpdateDto;
import com.ramadan.api.entity.user.EmailUser;
import com.ramadan.api.entity.user.User;
import com.ramadan.api.exceptions.APIErrorException;

public interface IEmailUserService {

    EmailResponseDto findByUuid(String uuid) throws APIErrorException;


    Map<String, Object> findAll(int page, int size) throws APIErrorException;

    EmailResponseDto updateEmailUser(String uuid, EmailUpdateDto requestDto) throws APIErrorException;

    void deleteEmailUser(String uuid) throws APIErrorException;

	List<EmailResponseDto> getAllByUser(String uuid) throws APIErrorException;


    EmailResponseDto saveEmailUser(EmailRequestDto emailRequestDto) throws APIErrorException;
    
    
    EmailUser checkEmailUuid(String uuid) throws APIErrorException;
    
    EmailUser findByUserAndIsMain(User user) throws APIErrorException;  



}
