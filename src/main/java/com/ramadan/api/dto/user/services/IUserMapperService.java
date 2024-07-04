package com.ramadan.api.dto.user.services;

import com.ramadan.api.dto.user.FinaleConfirmationRegistration;
import com.ramadan.api.dto.user.model.KeycloakUser;
import com.ramadan.api.dto.user.model.UserDto;
import com.ramadan.api.dto.user.model.UserKeycloak;
import com.ramadan.api.dto.user.model.UserSignup;
import com.ramadan.api.dto.user.model.userInfoDto.FullInfoUser;
import com.ramadan.api.dto.user.model.userInfoDto.UserInfoResponse;
import com.ramadan.api.entity.user.User;
import com.ramadan.api.exceptions.APIErrorException;

public interface IUserMapperService {
    
	UserDto convertKeycloakToDto(UserKeycloak oUserDto);


    UserKeycloak fromUserInfoResponse(UserInfoResponse userInfo);

    User mapFromKeycloakUser(KeycloakUser keycloakUser);

    KeycloakUser mapToKeycloakUser(FinaleConfirmationRegistration user) throws APIErrorException;


	FullInfoUser mapUserToFullUserInfo(User user) throws APIErrorException;
}
