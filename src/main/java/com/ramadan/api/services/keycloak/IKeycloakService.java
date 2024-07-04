package com.ramadan.api.services.keycloak;



import com.ramadan.api.entity.user.User;
import com.ramadan.api.exceptions.APIErrorException;

public interface IKeycloakService {

	//UserKeycloak getUser(String token) throws APIErrorException;

	User getUserConnecter() throws APIErrorException;

}
