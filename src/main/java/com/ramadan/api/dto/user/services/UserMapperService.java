package com.ramadan.api.dto.user.services;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ramadan.api.dto.MapClassWithDto;
import com.ramadan.api.dto.company.model.CompanyResponseDto;
import com.ramadan.api.dto.user.FinaleConfirmationRegistration;
import com.ramadan.api.dto.user.PhoneUser.PhoneUserResponseDto;
import com.ramadan.api.dto.user.email.EmailResponseDto;
import com.ramadan.api.dto.user.model.KeycloakUser;
import com.ramadan.api.dto.user.model.UserDto;
import com.ramadan.api.dto.user.model.UserKeycloak;
import com.ramadan.api.dto.user.model.UserSignup;
import com.ramadan.api.dto.user.model.userInfoDto.FullInfoUser;
import com.ramadan.api.dto.user.model.userInfoDto.UserInfoResponse;
import com.ramadan.api.entity.company.Company;
import com.ramadan.api.entity.user.EmailUser;
import com.ramadan.api.entity.user.PhoneUser;
import com.ramadan.api.entity.user.User;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ErrorCode;
import com.ramadan.api.services.user.emailUser.IEmailUserService;
import com.ramadan.api.services.user.phone.IPhoneUserService;


@Service
public class UserMapperService implements IUserMapperService {
	
	@Autowired
	IPhoneUserService phoneRepository ;
	@Autowired
	IEmailUserService emailRepository ;
	@Autowired
	private  MapClassWithDto<Company, CompanyResponseDto> mapClassWithResponseDto;
	
    
    @Override
    public UserDto convertKeycloakToDto(UserKeycloak oUserKeycloak) {
        UserDto user = new UserDto();
        user.setGroups(oUserKeycloak.getAgences());
        user.setEmail(oUserKeycloak.getEmail());
        user.setName(oUserKeycloak.getGiven_name());
        user.setFamillyName(oUserKeycloak.getFamily_name());
        user.setEmailVerified(oUserKeycloak.getEmail_verified());
        user.setUsername(oUserKeycloak.getPreferred_username());
        user.setRoles(Collections.singletonList(oUserKeycloak.getRoles()));
        user.setIdKeycloak(oUserKeycloak.getSub());
        return user;
    }
    @Override
    public  UserKeycloak fromUserInfoResponse(UserInfoResponse userInfo) {
        UserKeycloak userKeycloak = new UserKeycloak();
        userKeycloak.setSub(userInfo.getSub());
        userKeycloak.setEmail_verified(userInfo.isEmail_verified());
        userKeycloak.setName(userInfo.getName());
        userKeycloak.setPreferred_username(userInfo.getPreferred_username());
        userKeycloak.setGiven_name(userInfo.getGiven_name());
        userKeycloak.setFamily_name(userInfo.getFamily_name());
        userKeycloak.setEmail(userInfo.getEmail());
        userKeycloak.setAgences(userInfo.getRealm_access().getRoles());
        userKeycloak.setRoles(userInfo.getResource_access().getAccount().getRoles());
        return userKeycloak;
    }
    @Override
    public  User mapFromKeycloakUser(KeycloakUser keycloakUser) {
        User user = new User();
        user.setUsername(keycloakUser.getUsername());
//        user.setEmail(keycloakUser.getEmail());
        user.setName(keycloakUser.getFirstName());
        user.setFamillyName(keycloakUser.getLastName());
//        user.setEmailVerified(keycloakUser.isEmailVerified());
        return user;
    }
    @Override
    public KeycloakUser mapToKeycloakUser(FinaleConfirmationRegistration registration) throws APIErrorException {
        PhoneUser phoneUser = phoneRepository.checkTelephoneUuid(registration.getKey());
        if (phoneUser == null || phoneUser.getUser() == null) {
            throw new APIErrorException(ErrorCode.A0022);
        }

        User user = phoneUser.getUser();
        EmailUser emailUser = emailRepository.findByUserAndIsMain(user);
        if (emailUser == null) {
            throw new APIErrorException(ErrorCode.A0011);
        }
        String email = emailUser.getEmail();

        KeycloakUser keycloakUser = new KeycloakUser();
        keycloakUser.setUsername(email);
        keycloakUser.setEmail(email);
        return keycloakUser;
    }
    
    @Override
    public FullInfoUser mapUserToFullUserInfo(User user) throws APIErrorException {
    	FullInfoUser fullInfoUser = new FullInfoUser();
    	List<EmailResponseDto> emailList = emailRepository.getAllByUser(user.getUuid());
    	List<PhoneUserResponseDto> phonelist = phoneRepository.getAllByUser(user.getUuid());
    	fullInfoUser.setEmailsList(emailList);
    	fullInfoUser.setPhoneList(phonelist);
    	CompanyResponseDto companyResponseDto = mapClassWithResponseDto.convertToDto(user.getCompany(), CompanyResponseDto.class);
    	fullInfoUser.setCompany(companyResponseDto);
    	fullInfoUser.setName(user.getName());
    	fullInfoUser.setUsername(user.getUsername());
    	fullInfoUser.setFamillyName(user.getFamillyName());
    	
    	return fullInfoUser ;
    	
    }

}
