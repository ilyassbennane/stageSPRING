package com.ramadan.api.helpers.sendEmail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ramadan.api.entity.user.OTP;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailSend implements IEmailSend{
	
	@Autowired
    private JavaMailSender mailSender;
	
	@Override
    public void sendEmail(String email ,String name , OTP otp) throws MessagingException {
		Mail mail = prepareMail(email,name,otp);
		MimeMessage message = mailSender.createMimeMessage();
        message.setRecipients(MimeMessage.RecipientType.TO , mail.getReceiver());
        message.setSubject(mail.getSubject());
        message.setContent(mail.getBody(), "text/html; charset=utf-8");
        
        mailSender.send(message);
    }
	
	Mail prepareMail(String email , String name , OTP otp) {
		String title = "User" + name ;
		String htmlContent = "<!DOCTYPE html>\r\n"
				+ "		<html>\r\n"
				+ "		<head>\r\n"
				+ "		  <title>Your Email Template</title>\r\n"
				+ "		  <style>\r\n"
				+ "		    /* CSS styles for your email template */\r\n"
				+ "		    body {\r\n"
				+ "		      font-family: Arial, sans-serif;\r\n"
				+ "		      background-color: #F5F5F5;\r\n"
				+ "		    }\r\n"
				+ "		    .header {\r\n"
				+ "		      background-color: #E9E9E9;\r\n"
				+ "		      padding: 10px;\r\n"
				+ "		      text-align: center;\r\n"
				+ "		    }\r\n"
				+ "		    .content {\r\n"
				+ "		      padding: 20px;\r\n"
				+ "		    }\r\n"
				+ "		    .footer {\r\n"
				+ "		      background-color: #E9E9E9;\r\n"
				+ "		      padding: 10px;\r\n"
				+ "		      text-align: center;\r\n"
				+ "		    }\r\n"
				+ "		  </style>\r\n"
				+ "		</head>\r\n"
				+ "		<body>\r\n"
				+ "		  <div class=\"header\">\r\n"
				+ "		    <h1>NitroSales</h1>\r\n"
				+ "		  </div>\r\n"
				+ "		  <div class=\"content\">\r\n"
				+ "		    <h2>Hello,"+name+"</h2>\r\n"
				+ "		    <p>Please use the verification code below on the NitroSales application:</p>\r\n"
				+ "		    <p>"+otp.getOtp()+"</p>\r\n"
				+ "		    <p>If you didn't request this, you can ignore this email or let us know.</p>\r\n"
				+ "		    <p>NitroSales</p>\r\n"
				+ "		  </div>\r\n"
				+ "		  <div class=\"footer\">\r\n"
				+ "		    <p>© 2024 NitroSales. All rights reserved.</p>\r\n"
				+ "		  </div>\r\n"
				+ "		</body>\r\n"
				+ "		</html>";

		Mail mail = 
			Mail.builder()
				.receiver(email)
				.body(htmlContent)
				.subject(title)
				.build();
		return mail;
	}


}