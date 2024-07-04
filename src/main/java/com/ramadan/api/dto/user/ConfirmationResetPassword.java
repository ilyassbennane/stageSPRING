package com.ramadan.api.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Data
@Schema(name = "ConfirmationResetPassword")
public class ConfirmationResetPassword {

		private String Token;
	    
	    @JsonProperty("password")
	    @Schema(
	        name = "password",
	        type = "string",
	        description = "password",
	        example = "@1223456@",
	        required = true
	    )
	    private String password;
	    
	    @JsonProperty("confirmationPassword")
	    @Schema(
	        name = "confirmationPassword",
	        type = "string",
	        description = "password",
	        example = "@1223456@",
	        required = true
	    )
	    private String confirmationPassword;


	
}
