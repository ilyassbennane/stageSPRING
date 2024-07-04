package com.ramadan.api.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ramadan.api.dto.company.model.CompanyResponseDto;
import com.ramadan.api.dto.secteur.model.SecteurResponseDto;
import com.ramadan.api.dto.user.ConfirmationResetPassword;
import com.ramadan.api.dto.user.FinaleConfirmationRegistration;
import com.ramadan.api.dto.user.KeyDto;
import com.ramadan.api.dto.user.KeyTokenDto;
import com.ramadan.api.dto.user.TrueResetPassword;
import com.ramadan.api.dto.user.UserUpdateDto;
import com.ramadan.api.dto.user.ValiderOTPDto;
import com.ramadan.api.dto.user.model.LoginDto;
import com.ramadan.api.dto.user.model.RefreshTocken;
import com.ramadan.api.dto.user.model.ReponseTokenDto;
import com.ramadan.api.dto.user.model.ResetPassword;
import com.ramadan.api.dto.user.model.TokenDto;
import com.ramadan.api.dto.user.model.UserSignup;
import com.ramadan.api.dto.user.model.userInfoDto.FullInfoUser;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ApiError;
import com.ramadan.api.services.user.UserService;
import com.ramadan.api.services.user.otp.OTPService;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

@Tag(name = "User", description = "APIs - Login - Get User Infos")
@ApiResponses({
		@ApiResponse(responseCode = "401", description = "Authentication is required to access the resource.", content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)) }),
		@ApiResponse(responseCode = "400", description = "The syntax of the request is incorrect.", content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)) }),
		@ApiResponse(responseCode = "404", description = "Resource not found.", content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)) }),
		@ApiResponse(responseCode = "500", description = "A system error occurred.", content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)) }),
		@ApiResponse(responseCode = "501", description = "Requested functionality is not supported by the server.", content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)) }) })
@RestController
@RequestMapping(path = "${URL-BASE}/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UtilisateurController {

	@Autowired
	UserService userService;

	/**
	 * Endpoint for user authentication. This method is used to authenticate a user
	 * and return a token for accessing protected resources.
	 *
	 * @param loginDto The {@link LoginDto} containing the user's login credentials.
	 * @return A {@link ResponseEntity} containing the token information if the
	 *         authentication is successful.
	 * @throws APIErrorException if an error occurs during the authentication
	 *                           process.
	 */
	@PostMapping("auth/login")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Token Found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = TokenDto.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)) }) })
	@Operation(summary = "Get Token", description = "Return Token access", method = "login", tags = {})
	public ResponseEntity<ReponseTokenDto> login(@Valid @RequestBody(required = true) LoginDto loginDto)
			throws APIErrorException {
		ReponseTokenDto oToken = userService.login(loginDto);

		return new ResponseEntity<>(oToken, HttpStatus.OK);
	}

	/**
	 * Endpoint for user logout. This method is used to log out a user by providing
	 * the refresh token.
	 *
	 * @param refreshToken The refresh token used to identify the user's session.
	 * @throws APIErrorException if an error occurs during the logout process.
	 */
	@PostMapping("/logout")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Successfully logged out"),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)) }) })
	@Operation(summary = "Logout", description = "Logout a user by providing the refresh token", tags = {})
	public void logout(@RequestBody RefreshTocken refreshToken) throws APIErrorException {
		userService.logout(refreshToken);
	}

	/**
	 * Endpoint to refresh the access token for an admin user. This method is used
	 * to refresh the access token for an admin user by providing the refresh token.
	 *
	 * @param refreshToken The refresh token used to refresh the access token.
	 * @return A {@link ResponseEntity} containing the refreshed access token
	 *         information if successful.
	 * @throws APIErrorException if an error occurs during the token refresh
	 *                           process.
	 */
	@PostMapping("auth/refresh-token")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Successfully retrieved admin refresh token"),
			@ApiResponse(responseCode = "401", description = "Authentication is required to access the resource.", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)) }),
			@ApiResponse(responseCode = "404", description = "Resource not found.", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)) }) })
	@Operation(summary = "refresh access token", description = "Refresh access token for admin user", tags = {})
	public ResponseEntity<ReponseTokenDto> refreshAccessToken(@RequestBody RefreshTocken refreshToken) {
		try {
			ReponseTokenDto tokenDto = userService.refreshAccessToken(refreshToken);
			return ResponseEntity.ok(tokenDto);
		} catch (APIErrorException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Endpoint to retrieve user information. This method is used to get the full
	 * information of the currently connected user.
	 *
	 * @return A {@link FullInfoUser} object containing the full information of the
	 *         currently connected user.
	 * @throws APIErrorException if an error occurs while retrieving the user
	 *                           information.
	 */
	@GetMapping("/infos")
	@Operation(summary = "Get User Information", description = "Retrieve the full information of the currently connected user")
	public FullInfoUser getUserInfo() throws APIErrorException {
		FullInfoUser userInfo = userService.getInfoUserConnect();
		return userInfo;
	}

	/**
	 * Updates a user by company. This method is used to update a user's information
	 * within a specific company.
	 *
	 * @param userUpdateDto The {@link UserUpdateDto} containing the updated user
	 *                      information.
	 * @return A {@link ResponseEntity} indicating the result of the update
	 *         operation.
	 */
	@ApiOperation(value = "Get all utilisateur  by company", notes = "Get a list of all users by company.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully retrieved users", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = SecteurResponseDto.class)) }), })
	@PutMapping
	public ResponseEntity<String> updateUser(@RequestBody UserUpdateDto userUpdateDto) {
		try {
			userService.updateUser(userUpdateDto);
			return new ResponseEntity<>("Utilisateur modifié avec succès.", HttpStatus.OK);
		} catch (APIErrorException e) {
			return new ResponseEntity<>("Erreur lors de la modification de l'utilisateur.",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Endpoint to reset user password. This method is used to reset a user's
	 * password by providing the necessary password information.
	 *
	 * @param passwordInfo The {@link ResetPassword} object containing the necessary
	 *                     information to reset the user's password.
	 * @return A {@link ResponseEntity} indicating the result of the password reset
	 *         operation.
	 * @throws APIErrorException if an error occurs during the password reset
	 *                           process.
	 */
	@PostMapping("/update-password")
	public ResponseEntity<Void> resetPassword(@RequestBody ResetPassword passwordInfo) throws APIErrorException {
		userService.resetPassword(passwordInfo);
		return ResponseEntity.ok().build();
	}
	
	
	
	
	
	
	
	
	
	
	
	////////////////////////////////////////
	
	
	
	
	
	
    @Operation(summary = "Reset Password Demande", description = "Reset Password Demande REST API ")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Demande Saved", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = KeyDto.class)) }), })
    @PostMapping("users/auth/reset-password")
    public ResponseEntity<KeyDto> TrueresetPassword(@Valid @RequestBody TrueResetPassword resetPassword)
            throws APIErrorException, MessagingException {
        KeyDto okeyDto = userService.demandeResetPassword(resetPassword);
        return ResponseEntity.ok(okeyDto);
    }
    


    
    
    @Operation(summary = "Valider Reset Password", description = "Valider Reset Password REST API ")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Valider Change", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)) }), })
    @PostMapping("users/auth/valider-reset-password")
    public ResponseEntity<KeyTokenDto> ValiderOtpResetPassword(@Valid @RequestBody ValiderOTPDto validerOTPDto)
            throws APIErrorException {
    	KeyTokenDto okeyDto = userService.validerOTPResetPassword(validerOTPDto);
        return  ResponseEntity.ok(okeyDto);
    }
    
    
    
    
    @Operation(summary = "Confirmation Reset Password", description = "Confirmation Reset Password REST API ")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Password Change", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)) }), })
    @PostMapping("users/auth/confirme-reset-password")
    public ResponseEntity<Void> confirmationResetPassword(@Valid @RequestBody ConfirmationResetPassword confirmationResetPassword)
            throws APIErrorException {
        userService.confirmationResetPassword(confirmationResetPassword);
        return ResponseEntity.ok().build();
    }
	
    
    
    
    
	
	
	
	
	
	/////////////////////////////////////////////

	/**
	 * Endpoint to handle user signup requests. This method receives a
	 * {@link UserSignup} DTO containing the necessary information to create a new
	 * user, including their Keycloak and local user information, role assignments,
	 * and associated company.
	 *
	 * @param userSignup The {@link UserSignup} DTO with the new user's signup
	 *                   information.
	 * @return
	 * @return A {@link ResponseEntity} indicating the result of the signup
	 *         operation.
	 */
//	@PostMapping("auth/signup")
//     public ResponseEntity<Void> signup(@RequestBody UserSignup userSignup) throws APIErrorException {
//    try {
//        userService.signup(userSignup);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    } catch (APIErrorException e) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//    }
//}

//RegisterDatabase
	@Operation(summary = "Register to databse", description = "Register User REST API is used to register user in a database and send a otp to email")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Save the User", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CompanyResponseDto.class))),
			@ApiResponse(responseCode = "400", description = "Validation error", content = @Content(mediaType = "application/json")) })

	@PostMapping("/auth/register")
	public ResponseEntity<KeyDto> register(@RequestBody UserSignup userSignup)
			throws APIErrorException, MessagingException {

		KeyDto okeyDto = userService.addToDB(userSignup);

		return ResponseEntity.ok(okeyDto);
	}
	// FirstConfirmation

	@Operation(summary = "First Confirmation Registration User", description = "Check OTP Email and send OTP Phone")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Confirmation email OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = KeyDto.class))),
			@ApiResponse(responseCode = "400", description = "Validation error", content = @Content(mediaType = "application/json")) })
	@PostMapping("/users/auth/confirme-registration")
	public ResponseEntity<KeyDto> createUser(@Valid @RequestBody ValiderOTPDto confirmationRegistration)
			throws APIErrorException {
		KeyDto keyDto = userService.confirmerInscription(confirmationRegistration);
		return ResponseEntity.ok(keyDto);
	}

	////
//	    @PostMapping("/send-sems")
//	    public ResponseEntity<KeyDto> sendSems(@RequestBody UserSignup userSignup) {
//	        try {
//	            KeyDto keyDto = userService.sendSms(userSignup);
//	            return ResponseEntity.ok(keyDto);
//	        } catch (APIErrorException e) {
//	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//	        }
//	    }

	@Operation(summary = "Last Confirmation Registration User", description = "Check OTP Phone")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Save the User to Keycloak", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
			@ApiResponse(responseCode = "400", description = "Validation error", content = @Content(mediaType = "application/json")) })
	@PostMapping("/auth/confirm-inscription")
	public ResponseEntity<Void> confirmerInscriptionFinal(@RequestBody FinaleConfirmationRegistration confirmationOTP)
			throws APIErrorException {
		userService.confirmerInscriptionFinal(confirmationOTP);
		return ResponseEntity.ok().build();
	}

	/////

	/**
	 * Retrieves a list of all users with pagination and sorting options.
	 *
	 * @param page The page number for pagination (default: 0).
	 * @param size The number of items per page for pagination (default: 10).
	 * @param sort An array of sorting parameters in the format "field,direction"
	 *             (default: "uuid,desc").
	 * @return A {@link ResponseEntity} containing a map of user information and
	 *         pagination details if successful.
	 * @throws APIErrorException if an error occurs while retrieving the user
	 *                           information.
	 */
	@ApiOperation(value = "Get users", notes = "Get a list of users .")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved users", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@GetMapping("all")
	public ResponseEntity<Map<String, Object>> getAllUsers(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "uuid,desc") String[] sort) {
		try {
			Map<String, Object> response = userService.findAll(page, size, sort);
			return ResponseEntity.ok(response);
		} catch (APIErrorException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@ApiOperation(value = "Get users by company", notes = "Get a list of users by company UUID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved users by company", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@GetMapping("/users-company-list/{uuid}")
	public ResponseEntity<Map<String, Object>> getUsersByCompany(@PathVariable String uuid,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "uuid,desc") String[] sort) {
		try {
			Map<String, Object> response = userService.findAllByCompany(uuid, page, size, sort);
			return ResponseEntity.ok(response);
		} catch (APIErrorException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@ApiOperation(value = "Get users by profile", notes = "Get a list of users by profile UUID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved users by profile", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@GetMapping("/users-profile-list/{uuid}")
	public ResponseEntity<Map<String, Object>> getUsersByProfile(@PathVariable String uuid,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "uuid,desc") String[] sort) {
		try {
			Map<String, Object> response = userService.findAllByProfile(uuid, page, size, sort);
			return ResponseEntity.ok(response);
		} catch (APIErrorException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
