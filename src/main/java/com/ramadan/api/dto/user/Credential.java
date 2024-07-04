package com.ramadan.api.dto.user;
import lombok.Data;

@Data
public class Credential {

	private String type;
	
	private String value;
	
	private Boolean temporary;
}
