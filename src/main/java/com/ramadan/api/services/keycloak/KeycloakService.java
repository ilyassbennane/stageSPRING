package com.ramadan.api.services.keycloak;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ramadan.api.config.TokenFilter;
import com.ramadan.api.dto.user.model.UserKeycloak;
import com.ramadan.api.entity.user.User;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ApiKeyException;
import com.ramadan.api.exceptions.ErrorCode;
import com.ramadan.api.repository.user.UserRepository;

@Service
public class KeycloakService implements IKeycloakService {


	@Autowired
	UserRepository userRepository;

	public String getSubFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            return jwt.getClaimAsString("sub");
        }

        return null;
    }



	@Override
	public User getUserConnecter() throws APIErrorException {
		System.out.println(getSubFromToken());
		User userConnected = userRepository.findByIdKeycloak(getSubFromToken());
		if (Objects.isNull(userConnected)) {
			throw new APIErrorException(ErrorCode.A044);
		}
		return userConnected;
	}

}
