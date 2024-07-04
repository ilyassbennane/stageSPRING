package com.ramadan.api.services.user.Token;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ramadan.api.entity.user.DemandeResetPassword;
import com.ramadan.api.entity.user.Token;
import com.ramadan.api.entity.user.User;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ErrorCode;
import com.ramadan.api.repository.user.TokenRepository;

@Service
public class TokenService implements ITokenService {
	@Autowired
	private TokenRepository tokenRepository;

	@Value("${time.expiration}")
	private int time;

	@Override
	public Token generateTokenUser(User user) throws APIErrorException {

		Token storedToken = tokenRepository.findByUserAndExpiryTimeAfter(user, LocalDateTime.now());
		Token TokenCREATED = new Token();
		if (Objects.isNull(storedToken)) {
			LocalDateTime expiryTime = LocalDateTime.now().plusSeconds(time); 
			Token token = new Token();
			token.setUser(user);
			token.setExpiryTime(expiryTime);
			TokenCREATED = tokenRepository.save(token);
		} else
			TokenCREATED = storedToken;

		return TokenCREATED;
	}

	@Override
	public Token generateTokenResetPasswordDemande(DemandeResetPassword demandeResetPassword) throws APIErrorException {

		Token storedToken = tokenRepository.findByResetPasswordDemandeAndExpiryTimeAfter(demandeResetPassword,
				LocalDateTime.now());
		Token TokenCREATED = new Token();
		if (Objects.isNull(storedToken)) {
			LocalDateTime expiryTime = LocalDateTime.now().plusSeconds(time);
			Token token = new Token();
			token.setDemandeResetPassword(demandeResetPassword);
			token.setExpiryTime(expiryTime);
			TokenCREATED = tokenRepository.save(token);
		} else
			TokenCREATED = storedToken;

		return TokenCREATED;
	}

	@Override
	public Token validateToken(String token) throws APIErrorException {

		if (Objects.isNull(token)) {
			throw new APIErrorException(ErrorCode.A0070);
		}
		Token storedToken = tokenRepository.findByTokenAndExpiryTimeAfter(token, LocalDateTime.now());

		if (Objects.isNull(storedToken)) {
			throw new APIErrorException(ErrorCode.A0068);
		}
		return storedToken;
	}

}
