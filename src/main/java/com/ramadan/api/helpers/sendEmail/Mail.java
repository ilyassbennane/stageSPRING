package com.ramadan.api.helpers.sendEmail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Mail {
	
	private String subject;
	private String sender;
	private String receiver;
	private String body;
}