package com.ramadan.api.dto.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(name = "Login")
public class LoginDto {
	

	@JsonProperty("username")
	@Schema(name = "username", example = "user", required = true )
	@NotBlank(message = "Blank username")
	private String username;
	
	
	@JsonProperty("password")
	@Schema(name = "password", example = "123456", required = true )
	@NotBlank(message = "Blank password ")
	private String password;
	@Schema(name = "LatitudeGps", example = "123456", required = true )
	private String LatitudeGps ;
	@Schema(name = "LangitudeGps", example = "123456", required = true )
	private Double LangitudeGps ;
	@Schema(name = "deviceUuid", example = "	ufefhiuezhfuezy_ruz'higuhezupihcu", required = true )
	private Double deviceUuid ;
	@Schema(name = "IdLangue", example = "123456", required = true )
	private long IdLangue ;

	
}
