package com.ramadan.api.helpers.sendEmail;

import com.ramadan.api.entity.user.OTP;

import jakarta.mail.MessagingException;

public interface IEmailSend {
	
	void sendEmail(String email , String Name , OTP otp) throws MessagingException;


}
