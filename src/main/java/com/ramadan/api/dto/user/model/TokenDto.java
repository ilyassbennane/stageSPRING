package com.ramadan.api.dto.user.model;

import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class TokenDto {
	
	@Id
	@ApiModelProperty(
			  value = "Token d’accès : Access token",
			  name = "accessToken",
			  dataType = "String",
			  notes = "Le token d’accès permet au client d’accéder à la ressource protégée. Ce token a une durée de validité limitée et peut avoir une portée limitée. \n \n Cette notion de portée permet d’accorder un accès limité au client. Ainsi, un utilisateur peut autoriser un client à accéder à ses ressources qu’en lecture seule.",
			  example = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJrZzdLUXlIaFktaUoyN2ZYR1JNMjRsWEU3R0FpLUkxZzM2dHowNjd1aVg0In0.eyJleHAiOjE2MTM2NjQyNzcsImlhdCI6MTYxMzY2Mzk3NywianRpIjoiNjNmZjlmNTAtN2IyYy00MWE0LTk2MTktNzlkZDVmNDljNjgwIiwiaXNzIjoiaHR0cHM6Ly8xOTIuMTY4LjIyLjE4Ny9hdXRoL3JlYWxtcy94bWVuIiwic3ViIjoiYWRjOTBmYzQtMDg5OC00N2NiLWEzY2EtMTc2ZWM4MjlmMDcyIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiYWRtaW4tY2xpIiwic2Vzc2lvbl9zdGF0ZSI6ImNkYzQwZDk4LTUwMTItNGJlYi04NDM3LTkxMWViNDJlZjkxZiIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiKiJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiYWRtaW4iLCJ1c2VyIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWRtaW4tY2xpIjp7InJvbGVzIjpbImFkbWluIiwidXNlciJdfX0sInNjb3BlIjoiZW1haWwgcHJvZmlsZSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwibmFtZSI6IkVsIE1laGRpIEhSQUlEQSIsInByZWZlcnJlZF91c2VybmFtZSI6IjA2NTAzMDA5MjkiLCJnaXZlbl9uYW1lIjoiRWwgTWVoZGkiLCJmYW1pbHlfbmFtZSI6IkhSQUlEQSIsImVtYWlsIjoidGVzdDc4QGVxZG9tLmNvLm1hIn0.hQsY8CzA3lo0eky2mwUWI1R3ZDJQs9ZnyNarfYg9fBm-sd4iU4VX5MCwRha5iSmXXtQFa_pa5KQeZl2FUll65MlW2q5Yv8pvUxDc8XarV03ttb9F9dKD15jNazlzi8YCGXXcet0GncqqrCbYa1qG0YYcmk3QGuqbQJvgadK9vTIZOjRZMLuZHVxgLP7zDv87AP8F7BJlC-G2Wj2oB9lFk6bhFsToxx8yOTi3b0xtxLrSLBM0XBuoXo9zS2i65Hy1qUvKlXBaYLnLxHkW2tx-HZ6Uwf9If86urwrNLd2momkXqnXaHrcirQaTjWgbO-01VQCBJDp9iIcePi9poNzdHw")
	private String accessToken;
	
	
	@ApiModelProperty(
			  value = "Token de rafraîchissement : Refresh token",
			  name = "refreshToken",
			  dataType = "String",
			  notes = "Le token de rafraîchissement permet au client d’obtenir un nouveau token d’accès une fois que celui-ci a expiré. Sa durée de validité est aussi limitée mais est beaucoup plus élevée que celle du token d’accès. \n \n Son utilisation permet au client d’obtenir un nouveau token d’accès sans l’intervention du propriétaire de la ressource protégée.",
			  example = "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJiYTU0NTczMy01ODRlLTQ5NmEtYjkwZC0zN2Q1Y2E4NDk5NTAifQ.eyJleHAiOjE2MTM2NjU3NzcsImlhdCI6MTYxMzY2Mzk3NywianRpIjoiMDI1MDJlY2UtYTVhMy00NzM0LWExZjYtZjkwNGJjMDFhMzk1IiwiaXNzIjoiaHR0cHM6Ly8xOTIuMTY4LjIyLjE4Ny9hdXRoL3JlYWxtcy94bWVuIiwiYXVkIjoiaHR0cHM6Ly8xOTIuMTY4LjIyLjE4Ny9hdXRoL3JlYWxtcy94bWVuIiwic3ViIjoiYWRjOTBmYzQtMDg5OC00N2NiLWEzY2EtMTc2ZWM4MjlmMDcyIiwidHlwIjoiUmVmcmVzaCIsImF6cCI6ImFkbWluLWNsaSIsInNlc3Npb25fc3RhdGUiOiJjZGM0MGQ5OC01MDEyLTRiZWItODQzNy05MTFlYjQyZWY5MWYiLCJzY29wZSI6ImVtYWlsIHByb2ZpbGUifQ.htsVcH5YSC9jzRjuNaylDL8ETj1FQaI44TSTiMTSOxQ")
	private String refreshToken;
	
	
	@ApiModelProperty(
			  value = "La durée de vie du jeton",
			  name = "expiresIn",
			  dataType = "Long",
			  notes="La durée de vie du jeton est contrôlée par le paramètre d'inactivité de session SSO. 5 minutes = 5 * 60 = 300 secondes (la valeur expiresIn)",
			  example = "300")
	private Long expiresIn;
	
	@ApiModelProperty(
			  value = "La durée de vie du jeton d'actualisation",
			  name = "refreshExpiresIn",
			  dataType = "Long",
			  notes="La durée de vie du jeton d'actualisation est contrôlée par le paramètre d'inactivité de session SSO. 30 minutes = 30 * 60 = 1800 secondes (la valeur refreshExpiresIn)",
			  example = "1800")
	private Long refreshExpiresIn;
	
	
	 
	
	@ApiModelProperty(
			  value = "Type de flux d’autorisation, par exemple \"bearer\".",
			  name = "tokenType",
			  dataType = "String",
			  example = "bearer")
	private String tokenType;
	
	
	@ApiModelProperty(
			  value = "État de la session : Session State",
			  name = "sessionState",
			  dataType = "String",
			  example = "cdc40d98-5012-4beb-8437-911eb42ef91f")
	private String sessionState;
	
	@ApiModelProperty(
			  value = "La liste des scopes de l’application cliente",
			  name = "scope",
			  dataType = "String",
			  example = "email profile")
	private String scope;

}
