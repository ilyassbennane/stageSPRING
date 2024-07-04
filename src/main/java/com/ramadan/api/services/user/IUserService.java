package com.ramadan.api.services.user;

import java.util.List;
import java.util.Map;

import com.ramadan.api.dto.user.ConfirmationResetPassword;
import com.ramadan.api.dto.user.FinaleConfirmationRegistration;
import com.ramadan.api.dto.user.KeyDto;
import com.ramadan.api.dto.user.KeyTokenDto;
import com.ramadan.api.dto.user.TrueResetPassword;
import com.ramadan.api.dto.user.UserUpdateDto;
import com.ramadan.api.dto.user.ValiderOTPDto;
import com.ramadan.api.dto.user.model.KeycloakUser;
import com.ramadan.api.dto.user.model.LoginDto;
import com.ramadan.api.dto.user.model.RefreshTocken;
import com.ramadan.api.dto.user.model.ReponseTokenDto;
import com.ramadan.api.dto.user.model.ResetPassword;
import com.ramadan.api.dto.user.model.RoleAssignment;
import com.ramadan.api.dto.user.model.UserDto;
import com.ramadan.api.dto.user.model.UserKeycloak;
import com.ramadan.api.dto.user.model.UserSignup;
import com.ramadan.api.dto.user.model.userInfoDto.FullInfoUser;
import com.ramadan.api.entity.user.User;
import com.ramadan.api.exceptions.APIErrorException;

import jakarta.mail.MessagingException;

public interface IUserService {

	UserDto updateUser(UserUpdateDto userUpdateDto) throws APIErrorException;

	void updateUserKeycloack(KeycloakUser keycloakUser, String id);

	KeycloakUser valideInsert(UserUpdateDto userUpdateDto) throws APIErrorException;

	void resetPassword(ResetPassword resetPassword) throws APIErrorException;

	void roleMappings(String id, List<RoleAssignment> roleAssignment) throws APIErrorException;

	Map<String, Object> findAllByCompany(String uuid, int page, int size, String[] sort) throws APIErrorException;

	Map<String, Object> findAll(int page, int size, String[] sort) throws APIErrorException;

	Map<String, Object> findAllByProfile(String uuid, int page, int size, String[] sort) throws APIErrorException;

	boolean passwordCheck(String password, String passwordCofirmation) throws APIErrorException;

	void signup(UserSignup userSignup) throws APIErrorException;

	void addUser(KeycloakUser signupDto) throws APIErrorException;

	User insertUser(UserKeycloak oUserKeycloak);

	UserDto getInfo(String token) throws APIErrorException;

	FullInfoUser getInfoUserConnect() throws APIErrorException;

	void logout(RefreshTocken refreshToken) throws APIErrorException;

	ReponseTokenDto refreshAccessToken(RefreshTocken refreshToken) throws APIErrorException;

	ReponseTokenDto login(LoginDto loginDto) throws APIErrorException;

	void adminLogin() throws APIErrorException;


    UserDto findByUuid(String uuid) throws APIErrorException;
    
    
    KeyDto addToDB(UserSignup userSignup) throws APIErrorException,MessagingException;
    
    KeyDto confirmerInscription(ValiderOTPDto confirmationOTP) throws APIErrorException ;
    
    void confirmerInscriptionFinal(FinaleConfirmationRegistration confirmationOTP) throws APIErrorException ;
    
    KeyDto sendSms(UserSignup userSignup) throws APIErrorException;
    
    
    KeyDto demandeResetPassword(TrueResetPassword resetPassword) throws APIErrorException, MessagingException ;
    
    
    KeyTokenDto validerOTPResetPassword(ValiderOTPDto validerOTPDto) throws APIErrorException;
    
    void confirmationResetPassword(ConfirmationResetPassword confirmationResetPassword) throws APIErrorException;
    
    void resetPasswordKeycloak(String idKeycloak, String password) throws APIErrorException;
    
//    String GetIDKeycloak(String username, String password) throws APIErrorException ;
//    
//    UserKeycloak getUser(String Token) throws APIErrorException;
    }
