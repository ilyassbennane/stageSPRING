package com.ramadan.api.dto.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ReponseTokenDto {
    private String access_token;
    private int expires_in;
    private int refresh_expires_in;
    private String refresh_token;
    private String token_type;
    private int not_before_policy;
    private String session_state;
    private String scope;

}
