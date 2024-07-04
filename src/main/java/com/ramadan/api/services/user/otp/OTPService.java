package com.ramadan.api.services.user.otp;

import java.time.LocalDateTime;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ramadan.api.dto.user.ValiderOTPDto;
import com.ramadan.api.entity.user.EmailUser;
import com.ramadan.api.entity.user.OTP;
import com.ramadan.api.entity.user.PhoneUser;
import com.ramadan.api.entity.user.User;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ErrorCode;
import com.ramadan.api.helpers.sendEmail.IEmailSend;
import com.ramadan.api.helpers.sendPhone.ISmsSend;
import com.ramadan.api.repository.user.EmailRepository;
import com.ramadan.api.repository.user.OTPRepository;
import com.ramadan.api.repository.user.PhoneRepository;
import com.ramadan.api.services.user.IUserService;
import com.ramadan.api.services.user.emailUser.IEmailUserService;
import com.ramadan.api.services.user.phone.IPhoneUserService;

import jakarta.mail.MessagingException;

@Service
public class OTPService implements IOTPService {
	@Autowired
	private OTPRepository otpRepository;

	@Autowired
	private PhoneRepository telephoneRepository;
	@Autowired
	private IPhoneUserService telephoneService;
	@Autowired
	private EmailRepository emailRepository;
	@Autowired
	private IEmailUserService emailService;
	@Autowired
	private IEmailSend emailSend;

	@Autowired
	private ISmsSend smsSend;

	@Value("${time.expiration}")
	private int time;

	@Override
	public void generateAndSendOTPTelephone(String telephoneUuid) throws APIErrorException{
		PhoneUser telephone = telephoneService.checkTelephoneUuid(telephoneUuid);
		User oUser = telephone.getUser();

		OTP storedOTP = otpRepository.findByTelephoneAndExpiryTimeAfter(telephone.getUuid(), LocalDateTime.now());
		OTP OTPCREATED = new OTP();
		if (Objects.isNull(storedOTP)) {
			LocalDateTime expiryTime = LocalDateTime.now().plusSeconds(time);

			OTP otp = new OTP();
			otp.setTelephone(telephone);
			otp.setExpiryTime(expiryTime);

			OTPCREATED = otpRepository.save(otp);
		} else {
			OTPCREATED = storedOTP;
		}

		String otpMessage = "Your OTP code is: " + OTPCREATED.getOtp();
		smsSend.sendSms(telephone.getNumbre(), otpMessage);
	}

	@Override
	public Boolean validateOTPPhone(ValiderOTPDto validerOTPDto) throws APIErrorException {
		
		
		PhoneUser telephone = telephoneService.checkTelephoneUuid(validerOTPDto.getKey());

		OTP storedOTP = otpRepository.findByTelephoneAndExpiryTimeAfter(telephone.getUuid(), LocalDateTime.now());
	
		if (Objects.isNull(storedOTP)) {
			throw new APIErrorException(ErrorCode.A0066);
		}
		if (!"000000".equals(validerOTPDto.getOtpValue())) {
			throw new APIErrorException(ErrorCode.A0067);
		}

		storedOTP.setValid(true);
		telephone.setValid(true);

		PhoneUser telephonePrincipal = telephoneRepository.findPrincipal(telephone.getUser());
		if (Objects.isNull(telephonePrincipal)) {
			telephone.setMain(true);
		}
		telephoneRepository.save(telephone);
		return true;
	}

	@Override
	public void generateAndSendOTPEmail(String emailUuid) throws APIErrorException, MessagingException {

		EmailUser email = emailService.checkEmailUuid(emailUuid);

		User oUser = email.getUser();

		OTP storedOTP = otpRepository.findByEmailAndExpiryTimeAfter(email.getUuid(), LocalDateTime.now());
		OTP OTPCREATED = new OTP();

		if (Objects.isNull(storedOTP)) {
			LocalDateTime expiryTime = LocalDateTime.now().plusSeconds(time); // Expiration dans 5 minutes

			OTP otp = new OTP();
			otp.setEmail(email);
			otp.setExpiryTime(expiryTime);

			OTPCREATED = otpRepository.save(otp);

		} else
			OTPCREATED = storedOTP;

		emailSend.sendEmail(email.getEmail(), oUser.getName(), OTPCREATED);
	}

	@Override
	public Boolean validateOTPEmail(ValiderOTPDto validerOTPDto) throws APIErrorException {

		EmailUser email = emailService.checkEmailUuid(validerOTPDto.getKey());

		OTP storedOTP = otpRepository.findByEmailAndExpiryTimeAfter(email.getUuid(), LocalDateTime.now());

		if (Objects.isNull(storedOTP)) {
			throw new APIErrorException(ErrorCode.A0066);
		}
		if (!storedOTP.getOtp().equals(validerOTPDto.getOtpValue())) {

			throw new APIErrorException(ErrorCode.A0067);
		}

		storedOTP.setValid(true);
		email.setValid(true);

		emailRepository.save(email);
		return true;
	}

}
