package com.ramadan.api.dto.user.model.userInfoDto;

import java.util.List;

import com.ramadan.api.dto.company.model.CompanyResponseDto;
import com.ramadan.api.dto.user.PhoneUser.PhoneUserResponseDto;
import com.ramadan.api.dto.user.email.EmailResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class FullInfoUser {
	
	    @Schema(description = "Username of the user", example = "User_2001", required = true )
	    private String username;

	    @Schema(description = "Name of the user", example = "John Doe", required = false )
	    private String name;

	    @Schema(description = "Family name of the user", example = "Doe", required = false )
	    private String famillyName;

	    @Schema(description = "Email address of the user", example = "user@example.com", required = true )
	    private List<EmailResponseDto> emailsList;

	   private CompanyResponseDto company ;
	  
	   private List<PhoneUserResponseDto> phoneList ;

}
