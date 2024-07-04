package com.ramadan.api.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
@Schema(name = "TrueResetPassword")
public class TrueResetPassword { 
	
	@JsonProperty("login")
	@Schema(name = "login", example = "0813429865", required = true )
	@NotBlank(message = "Blank login ")
	private String login;  
}
