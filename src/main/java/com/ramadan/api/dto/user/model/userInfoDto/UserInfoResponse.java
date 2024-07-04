package com.ramadan.api.dto.user.model.userInfoDto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class UserInfoResponse {
    private long exp;
    private long iat;
    private String jti;
    private String iss;
    private String aud;
    private String sub;
    private String typ;
    private String azp;
    private String session_state;
    private String acr;
    private List<String> allowed_origins;
    private RealmAccess realm_access;
    private ResourceAccess resource_access;
    private String scope;
    private String sid;
    private boolean email_verified;
    private String name;
    private String preferred_username;
    private String given_name;
    private String family_name;
    private String email;
    private String client_id;
    private String username;
    private String token_type;
    private boolean active;
}

