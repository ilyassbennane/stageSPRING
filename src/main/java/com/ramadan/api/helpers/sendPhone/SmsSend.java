package com.ramadan.api.helpers.sendPhone;

import org.springframework.stereotype.Service;

import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;

@Service
public class SmsSend implements ISmsSend {
	public void sendSms(String phoneNumber, String message) {
	    VonageClient client = VonageClient.builder()
	            .apiKey("5348cf64")
	            .apiSecret("x4kZCr5gfDMshh0f")
	            .build();

	    TextMessage textMessage = new TextMessage("Vonage", phoneNumber, message);

	    SmsSubmissionResponse response = client.getSmsClient().submitMessage(textMessage);

	    if (response.getMessages().get(0).getStatus() == MessageStatus.OK) {
	        System.out.println("Message sent successfully.");
	    } else {
	        System.out.println("Message failed with error: " + response.getMessages().get(0).getErrorText());
	    }
	}


}
