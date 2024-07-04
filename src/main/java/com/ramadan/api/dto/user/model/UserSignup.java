package com.ramadan.api.dto.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class UserSignup {



    @Schema(description = "Email address of the user", example = "user@example.com", required = true )
    private String email;

    @Schema(description = "Telephone number of the user", example = "1234567890", required = true )
    private String telephone;

    @Schema(description = "Uuid of the company associated with the user", example = "1111111111111111", required = true )
    private String companyUuid;

}
