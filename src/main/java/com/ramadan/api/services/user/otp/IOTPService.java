package com.ramadan.api.services.user.otp;

import com.ramadan.api.dto.user.ValiderOTPDto;
import com.ramadan.api.exceptions.APIErrorException;

import jakarta.mail.MessagingException;

public interface IOTPService {
	
	void generateAndSendOTPTelephone(String phoneNumber) throws APIErrorException, MessagingException;

	Boolean validateOTPPhone(ValiderOTPDto validerOTPDto) throws APIErrorException ;
	
	void generateAndSendOTPEmail(String email) throws APIErrorException , MessagingException;

	Boolean validateOTPEmail(ValiderOTPDto validerOTPDto) throws APIErrorException ;

}
