package com.ramadan.api.services.user.Token;

import com.ramadan.api.entity.user.DemandeResetPassword;
import com.ramadan.api.entity.user.Token;
import com.ramadan.api.entity.user.User;
import com.ramadan.api.exceptions.APIErrorException;

public interface ITokenService {
	
	Token generateTokenUser(User user) throws APIErrorException
	;
	
	Token generateTokenResetPasswordDemande(DemandeResetPassword  demandeResetPassword) throws APIErrorException;

	Token validateToken(String token) throws APIErrorException ;

}