package com.ramadan.api.services.user;

import static org.apache.http.HttpHeaders.AUTHORIZATION;


import static org.keycloak.OAuth2Constants.CLIENT_SECRET;
import static org.keycloak.OAuth2Constants.PASSWORD;
import static org.keycloak.OAuth2Constants.TOKEN;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ramadan.api.config.TokenFilter;
import com.ramadan.api.dto.IMapClassWithDto;
import com.ramadan.api.dto.MapClassWithDto;
import com.ramadan.api.dto.company.model.CompanyResponseDto;
import com.ramadan.api.dto.user.ConfirmationResetPassword;
import com.ramadan.api.dto.user.Credential;
import com.ramadan.api.dto.user.FinaleConfirmationRegistration;
import com.ramadan.api.dto.user.KeyDto;
import com.ramadan.api.dto.user.KeyTokenDto;
import com.ramadan.api.dto.user.RefreshTokenDto;
import com.ramadan.api.dto.user.TrueResetPassword;
import com.ramadan.api.dto.user.UserUpdateDto;
import com.ramadan.api.dto.user.ValiderOTPDto;
import com.ramadan.api.dto.user.model.KeycloakUser;
import com.ramadan.api.dto.user.model.LoginDto;
import com.ramadan.api.dto.user.model.PasswordInfo;
import com.ramadan.api.dto.user.model.RefreshTocken;
import com.ramadan.api.dto.user.model.ReponseTokenDto;
import com.ramadan.api.dto.user.model.ResetPassword;
import com.ramadan.api.dto.user.model.RoleAssignment;
import com.ramadan.api.dto.user.model.TokenDto;
import com.ramadan.api.dto.user.model.UserDto;
import com.ramadan.api.dto.user.model.UserKeycloak;
import com.ramadan.api.dto.user.model.UserSignup;
import com.ramadan.api.dto.user.model.userInfoDto.FullInfoUser;
import com.ramadan.api.dto.user.model.userInfoDto.UserInfoResponse;
import com.ramadan.api.dto.user.services.IUserMapperService;
import com.ramadan.api.entity.company.Company;
import com.ramadan.api.entity.user.DemandeResetPassword;
import com.ramadan.api.entity.user.EmailUser;
import com.ramadan.api.entity.user.PhoneUser;
//import com.ramadan.api.entity.user.Profile;
import com.ramadan.api.entity.user.Token;

import com.ramadan.api.entity.user.User;
import com.ramadan.api.entity.user.profile.Profile;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ApiKeyException;
import com.ramadan.api.exceptions.ErrorCode;
import com.ramadan.api.helpers.Utils;
import com.ramadan.api.repository.company.CompanyRepository;
import com.ramadan.api.repository.user.DemandeResetPasswordRepository;
import com.ramadan.api.repository.user.EmailRepository;
import com.ramadan.api.repository.user.Historique_LoginRepository;
import com.ramadan.api.repository.user.PhoneRepository;
import com.ramadan.api.repository.user.ProfileRepository;
import com.ramadan.api.repository.user.UserRepository;
import com.ramadan.api.services.company.ICompanyService;
import com.ramadan.api.services.user.Token.ITokenService;
import com.ramadan.api.services.user.emailUser.EmailUserService;
import com.ramadan.api.services.user.otp.OTPService;

import jakarta.mail.MessagingException;

@Service()
public class UserService implements IUserService {

	private static final String CLIENT_ID = "client_id";
	private static final String REFRESH_TOKEN = "refresh_token";
	private static final String GRANT_TYPE = "grant_type";

	@Value("${bff_keycloak.token-uri}")
	private String keycloakTokenUri;

	@Value("${keycloak.realm}")
	private String realm;

	@Value("${keycloak.resource}")
	private String clientID;
	@Value("${keycloak.logout.url}")
	private String logoutUrl;
	@Value("${keyloack.introspection_endpoint}")
	private String userInfoUrl;
	@Value("${keycloak.admin.secret}")
	private String secret_key;
	@Value("${keycloak.admin.ressource}")
	private String admin_Client;
	@Value("${keycloack.creat_user.url}")
	private String addUserUrl;
	@Value("${keycloack.update_user.url}")
	private String UpdateUserUrl;
	@Value("${keycloack.email.verifie.url}")
	private String emailVerifie;
	@Value("${keycloack.reset_password.url}")
	private String passwordReset;
	@Value("${keycloack.email.redirect_uri}")
	private String redirect_uri;
	@Value("${keycloack.role-mappings.url}")
	private String roleMapping;
	@Value("${role-mappings.url}")
	private String roleMappingClients;
	@Value("${keycloack.role-id}")
	private String roleId;
	@Value("${otp.decription}")
	private String decriptionOtp;
	
	@Value("${bff_keycloak.create-uri}")
	private String keycloakcreateUri;

	String bearer = "Bearer ";
	private Map<String, String> otpMap = new HashMap<>();
	private static String accessToken;
	private static String accessTokenAdmin;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	IMapClassWithDto<Token,KeyTokenDto > TokenKeyMapper;

	@Autowired
	private IMapClassWithDto<TokenDto, ReponseTokenDto> mapperToken;

	@Autowired
	private IMapClassWithDto<User, UserDto> userServiceMapper;

	@Autowired
	private MapClassWithDto<Company, CompanyResponseDto> mapClassWithResponseDto;

	@Autowired
	private IUserMapperService userMapperService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private Historique_LoginRepository historiqueLoginRepository;

	@Autowired
	private ICompanyService companyService;

	@Autowired
	private OTPService otpService;

	@Autowired
	private CompanyRepository companyRepository;
	
    @Autowired
    ITokenService tokenService;

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private PhoneRepository phoneRepository;

	@Autowired
	private EmailRepository emailRepository;

	@Autowired
	private EmailUserService emailuserService;
	
	@Autowired
	DemandeResetPasswordRepository demandeResetPasswordRepository;

	@Override
	public ReponseTokenDto login(LoginDto loginDto) throws APIErrorException {

		ReponseTokenDto oReponseToken = null;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("username", loginDto.getUsername());
		map.add(PASSWORD, loginDto.getPassword());
		map.add(CLIENT_ID, clientID);
		map.add(GRANT_TYPE, "password");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

//		try {
		oReponseToken = restTemplate.postForEntity(keycloakTokenUri, request, ReponseTokenDto.class).getBody();
		System.out.println(oReponseToken);
		if (oReponseToken != null) {
			accessToken = oReponseToken.getAccess_token();
		}
//		} catch (Exception e) {
//			throw new APIErrorException(ErrorCode.E444);
//		}

		return oReponseToken;
	}

	@Override
	public ReponseTokenDto refreshAccessToken(RefreshTocken refreshTocken) throws APIErrorException {

		ReponseTokenDto oReponseToken = null;
		String refresht = refreshTocken.getRefresh_token();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add(REFRESH_TOKEN, refresht);
		map.add(CLIENT_ID, clientID);
		map.add(GRANT_TYPE, REFRESH_TOKEN);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

		try {
			oReponseToken = restTemplate.postForEntity(keycloakTokenUri, request, ReponseTokenDto.class).getBody();
			accessToken = oReponseToken.getRefresh_token();
		} catch (Exception e) {
			throw new APIErrorException(ErrorCode.E444);
		}
		return oReponseToken;
	}

	@Override
	public void logout(RefreshTocken refreshToken) throws APIErrorException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add(REFRESH_TOKEN, refreshToken.getRefresh_token());
		map.add(CLIENT_ID, clientID);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

		try {
			restTemplate.postForEntity(logoutUrl, request, Void.class);
		} catch (Exception e) {
			throw new APIErrorException(ErrorCode.E444);
		}
	}

	@Override
	public void adminLogin() throws APIErrorException {
		LoginDto loginDto = new LoginDto();
		loginDto.setPassword("3ichamohamed@22@ahmed");
		loginDto.setUsername("admin");
		ReponseTokenDto tokenDto = login(loginDto);
		accessTokenAdmin = tokenDto.getAccess_token();
	}

	@Override
	public FullInfoUser getInfoUserConnect() throws APIErrorException {
		UserInfoResponse userInfoResponse = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add(TOKEN, TokenFilter.getToken());
		map.add(CLIENT_ID, admin_Client);
		map.add(CLIENT_SECRET, secret_key);
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

		try {
			userInfoResponse = restTemplate.postForEntity(userInfoUrl, request, UserInfoResponse.class, realm)
					.getBody();

		} catch (Exception e) {
			throw new APIErrorException(ErrorCode.E444);
		}
		if (!userInfoResponse.isActive()) {
			throw new APIErrorException(ErrorCode.E401);
		}
		UserKeycloak userKeycloak = userMapperService.fromUserInfoResponse(userInfoResponse);
		insertUser(userKeycloak);
		User user = userRepository.findByIdKeycloak(userInfoResponse.getSub());
		FullInfoUser fullInfoUser = userMapperService.mapUserToFullUserInfo(user);
		return fullInfoUser;
	}

	@Override
	public UserDto getInfo(String token) throws APIErrorException {
		UserInfoResponse userInfoResponse = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add(TOKEN, token);
		map.add(CLIENT_ID, admin_Client);
		map.add(CLIENT_SECRET, secret_key);
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

		try {
			userInfoResponse = restTemplate.postForEntity(userInfoUrl, request, UserInfoResponse.class, realm)
					.getBody();

		} catch (Exception e) {
			throw new APIErrorException(ErrorCode.E444);
		}
		UserKeycloak userKeycloak = userMapperService.fromUserInfoResponse(userInfoResponse);
		return userMapperService.convertKeycloakToDto(userKeycloak);
	}

	@Override
	public User insertUser(UserKeycloak oUserKeycloak) {
		User ouserResponse;
		User ouser = userRepository.findByIdKeycloak(oUserKeycloak.getSub());

		if (ouser != null) {
//			ouser.setGroups(oUserKeycloak.getAgences());
//			ouser.setEmail(oUserKeycloak.getEmail());
			ouser.setName(oUserKeycloak.getGiven_name());
			ouser.setFamillyName(oUserKeycloak.getFamily_name());
//			ouser.setEmailVerified(oUserKeycloak.getEmail_verified());
			ouser.setUsername(oUserKeycloak.getPreferred_username());
//			ouser.setRoles(oUserKeycloak.getRoles());
			ouser.setIdKeycloak(oUserKeycloak.getSub());
			ouserResponse = userRepository.save(ouser);
		} else {
			UserDto ouserDto = userMapperService.convertKeycloakToDto(oUserKeycloak);
			ouser = userServiceMapper.convertToEntity(ouserDto, User.class);
			ouserResponse = userRepository.save(ouser);
		}

		return ouserResponse;
	}

	@Override
	public void addUser(KeycloakUser signupDto) throws APIErrorException {
	    adminLogin();
	    
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.set(AUTHORIZATION, bearer + accessTokenAdmin);
	    
	    HttpEntity<KeycloakUser> request = new HttpEntity<>(signupDto, headers);
	    
	    // Create user in Keycloak and get the response containing the user ID
	    ResponseEntity<String> response = restTemplate.exchange(addUserUrl, HttpMethod.POST, request, String.class);
	    
	    if (response.getStatusCode() != HttpStatus.CREATED) {
	        throw new APIErrorException(ErrorCode.E444);
	    } else {
	        // Extract the user ID from the response
	        String locationHeader = response.getHeaders().getLocation().toString();
	        String keycloakUserId = locationHeader.substring(locationHeader.lastIndexOf("/") + 1);
	        
	        // Update the user in your local database with the Keycloak user ID
	        User user = userRepository.findByUsername(signupDto.getEmail());
	        if (user != null) {
	            user.setIdKeycloak(keycloakUserId);
	            userRepository.save(user);
	        } else {
	            throw new APIErrorException(ErrorCode.A044);
	        }
	    }
	}


	/**
	 * Handles the signup process for a new user, including user creation in
	 * Keycloak, local user creation, role assignment, and company creation. This
	 * method performs several steps to ensure a new user is properly set up: 1.
	 * Checks if the provided password and confirmation password match. 2. Maps the
	 * {@link UserSignup} DTO to a {@link KeycloakUser} object for Keycloak. 3. Adds
	 * the new user to Keycloak using the {@code addUser} method. 4. Logs in the new
	 * user to obtain access tokens. 5. Retrieves the new user's information from
	 * Keycloak. 6. Assigns the specified role to the new user in Keycloak. 7.
	 * Converts the Keycloak user information to a local {@link User} entity and
	 * saves it. 8. Creates a new company associated with the user.
	 *
	 * @param userSignup The {@link UserSignup} DTO containing information for the
	 *                   new user's signup process.
	 * @throws APIErrorException If any step in the process fails, an
	 *                           {@link APIErrorException} is thrown with the
	 *                           appropriate error code.
	 */

	@Override
	public KeyDto addToDB(UserSignup userSignup) throws APIErrorException, MessagingException {
		Company company = companyRepository.findByUuid(userSignup.getCompanyUuid());
		if (Objects.isNull(company)) {
			throw new APIErrorException(ErrorCode.A0061);

		}

		User user = new User();
		user.setCompany(company);
		user.setUsername(userSignup.getEmail());
		userRepository.save(user);

		EmailUser emailUser = new EmailUser();
		emailUser.setUser(user);
		emailUser.setEmail(userSignup.getEmail());
		emailUser.setMain(true);
		emailRepository.save(emailUser);

		PhoneUser phoneUser = new PhoneUser();
		phoneUser.setUser(user);
		phoneUser.setNumbre(userSignup.getTelephone());
		phoneUser.setMain(true);
		phoneRepository.save(phoneUser);
		otpService.generateAndSendOTPEmail(emailUser.getUuid());
		KeyDto keyDto = new KeyDto();
		keyDto.setKey(emailUser.getUuid());
		return keyDto;

	}

	@Override
	public KeyDto confirmerInscription(ValiderOTPDto confirmationOTP) throws APIErrorException {

//    	if (confirmationOTP.getPassword() == null && !confirmationOTP.getPassword().equals(confirmationOTP.getConfirmationPassword())) {
//			throw new APIErrorException(ErrorCode.A0020);
//		}
		EmailUser email = emailuserService.checkEmailUuid(confirmationOTP.getKey());
		System.out.println(email.getUuid());
//    	User user = email.getUser();
//        user.setUserNamekeycloak(user.getUuid());     
//      userRepository.save(user);

		ValiderOTPDto validerOTPDto = new ValiderOTPDto();
		validerOTPDto.setKey(confirmationOTP.getKey());
		validerOTPDto.setOtpValue(confirmationOTP.getOtpValue());
		otpService.validateOTPEmail(validerOTPDto);

//        String password = confirmationOTP.getPassword();
//    	userService.createUser(user,password);

//		user.setValid(true);
//		user.setUuidKeycloak(userService.GetIDKeycloak(user.getUserNamekeycloak(), password));
//		
//	    userRepository.save(user);
        PhoneUser phoneUser = phoneRepository.findByUserUuid(email.getUser().getUuid());
		if (phoneUser == null) {
			throw new APIErrorException(ErrorCode.A0028); 
		}

		otpService.generateAndSendOTPTelephone(phoneUser.getUuid());

		KeyDto keyDto = new KeyDto();
		keyDto.setKey(phoneUser.getUuid());
		return keyDto;
	}

	@Override
	public KeyDto sendSms(UserSignup userSignup) throws APIErrorException {
		PhoneUser phoneUser = phoneRepository.findByNumber(userSignup.getTelephone());
		if (Objects.isNull(phoneUser)) {
			System.out.println("z");
			throw new APIErrorException(ErrorCode.A0022);
		}
		System.out.println(phoneUser.getUuid());

		String telephoneUuid = phoneUser.getUuid();
		otpService.generateAndSendOTPTelephone(phoneUser.getUuid());

		KeyDto keyDto = new KeyDto();
		keyDto.setKey(telephoneUuid);
		return keyDto;

	}

	@Override
	public void confirmerInscriptionFinal(FinaleConfirmationRegistration confirmationOTP) throws APIErrorException {

		ValiderOTPDto validerOTPDto = new ValiderOTPDto();
		validerOTPDto.setKey(confirmationOTP.getKey());
		validerOTPDto.setOtpValue(confirmationOTP.getOtpValue());
		otpService.validateOTPPhone(validerOTPDto);
		passwordCheck(confirmationOTP.getPassword(), confirmationOTP.getConfirmationPassword());
		KeycloakUser keycloakUser = userMapperService.mapToKeycloakUser(confirmationOTP);
		PasswordInfo passwordInfo = new PasswordInfo();
		passwordInfo.setValue(confirmationOTP.getPassword());
		passwordInfo.setTemporary(false);
		passwordInfo.setType("password");
		List<Object> list = new ArrayList<>();
		list.add(passwordInfo);
		keycloakUser.setCredentials(list);
		keycloakUser.setEnabled(true);
		addUser(keycloakUser);
		

		

	};

	@Override
	public void signup(UserSignup userSignup) throws APIErrorException {
		if (userSignup == null) {
			throw new APIErrorException(ErrorCode.A333);
		} else {
//			passwordCheck(userSignup.getPassword(), userSignup.getConfirmPassword());
//			KeycloakUser keycloakUser = userMapperService.mapToKeycloakUser(userSignup);
//			PasswordInfo passwordInfo = new PasswordInfo();
//			passwordInfo.setValue(userSignup.getPassword());
//			passwordInfo.setTemporary(false);
//			passwordInfo.setType("password");
//			List<Object> list = new ArrayList<>();
//			list.add(passwordInfo);
//			keycloakUser.setCredentials(list);
//			keycloakUser.setEnabled(true);
//			addUser(keycloakUser);
//			LoginDto loginDto = new LoginDto();
//			loginDto.setPassword(userSignup.getPassword());
//			loginDto.setUsername(userSignup.getEmail());
//			ReponseTokenDto dto = login(loginDto);
//			UserDto userDto = getInfo(dto.getAccess_token());
//			RoleAssignment roleAssignment = new RoleAssignment();
//			roleAssignment.setId(roleId);
//			roleAssignment.setName("responsable");
//			List<RoleAssignment> roleAssignments = new ArrayList<>();
//			roleAssignments.add(roleAssignment);
//			roleMappings(userDto.getIdKeycloak(), roleAssignments);
//			User user = userServiceMapper.convertToEntity(userDto, User.class);
//			Company company = new Company();
//			company.setName(userSignup.getCompanyName());
//			Company savedCompany =companyRepository.save(company);
//			user.setCompany(savedCompany);
//			User savedUser = userRepository.save(user);
//			PhoneUser phoneUser = new PhoneUser();
//			phoneUser.setNumbre(userSignup.getTelephone());
//			phoneUser.setUser(savedUser);
//			EmailUser emailUser = new EmailUser();
//			emailUser.setEmail(userSignup.getEmail());
//			emailUser.setUser(savedUser);
//			emailRepository.save(emailUser);
//			phoneRepository.save(phoneUser);
		}
	}

	@Override
	public boolean passwordCheck(String password, String passwordCofirmation) throws APIErrorException {
		if (!password.equals(passwordCofirmation)) {
			throw new APIErrorException(ErrorCode.A020);
		}
		return true;
	}

	@Override
	public UserDto updateUser(UserUpdateDto userUpdateDto) throws APIErrorException {
		UserDto userDto = getInfo(TokenFilter.getToken());
		User user = userServiceMapper.convertToEntity(userDto, User.class);

		KeycloakUser keycloakUser = valideInsert(userUpdateDto);
		updateUserKeycloack(keycloakUser, user.getIdKeycloak());
//		 user.setEmail(userUpdateDto.getEmail());
		user.setName(userUpdateDto.getName());
		user.setFamillyName(userUpdateDto.getFamillyName());
		userRepository.save(user);
		return userServiceMapper.convertToDto(userRepository.save(user), UserDto.class);

	}

	@Override
	public void updateUserKeycloack(KeycloakUser keycloakUser, String id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(AUTHORIZATION, bearer + accessTokenAdmin);
		HttpEntity<KeycloakUser> request = new HttpEntity<>(keycloakUser, headers);
		restTemplate.exchange(UpdateUserUrl + "/" + id, HttpMethod.PUT, request, String.class);
	}

	@Override
	public KeycloakUser valideInsert(UserUpdateDto userUpdateDto) throws APIErrorException {
		KeycloakUser keycloakUser = new KeycloakUser();
		if (userUpdateDto == null) {
			throw new APIErrorException(ErrorCode.A044);
		} else if (userUpdateDto.getFamillyName() != null) {
			keycloakUser.setLastName(userUpdateDto.getFamillyName());
		} else if (userUpdateDto.getName() != null) {
			keycloakUser.setFirstName(userUpdateDto.getName());
		} else if (userUpdateDto.getEmail() != null) {
			keycloakUser.setEmail(userUpdateDto.getEmail());
		}

		return keycloakUser;
	}
	
//	@Override
//	public String GetIDKeycloak(String username, String password) throws APIErrorException {
//		
//		LoginDto login = new LoginDto();
//		login.setUsername(username);
//		login.setPassword(password);
//
//		TokenDto token = loginKeycloak(login);
//
//		UserKeycloak oUserKeycloak = getUser(token.getAccessToken());
//		
//		RefreshTokenDto rTokenDto = new RefreshTokenDto();
//		rTokenDto.setRefreshToken(token.getRefreshToken());
//		logout(rTokenDto);
//		
//		return oUserKeycloak.getSub();
//	}
	
//	@Override
//	public UserKeycloak getUser(String Token) throws APIErrorException{
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//		map.add("token", Token);
//		map.add(CLIENT_ID, admin_Client);
//		map.add(CLIENT_SECRET, s);
//
//		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
//		UserKeycloak userKeycloak = new UserKeycloak();
//		
//		try {
//			 userKeycloak = restTemplate.postForEntity(keycloakintrosUri, request, UserKeycloak.class).getBody();
//		} catch (Exception e) {
//			throw new ApiKeyException(ErrorCode.A0402);
//		}
//		
//		if (!userKeycloak.isActive()) {
//			throw new ApiKeyException(ErrorCode.A0403);
//		}
//		
//		return userKeycloak;
//
//	}

	@Override
	public void resetPassword(ResetPassword resetPassword) throws APIErrorException {
		UserDto userDto = getInfo(TokenFilter.getToken());
		LoginDto loginDto = new LoginDto();
		loginDto.setPassword(resetPassword.getOldPassword());
		loginDto.setUsername(userDto.getEmail());
		login(loginDto);
		System.out.println(userDto.getIdKeycloak());
		passwordCheck(resetPassword.getConfirmPassword(), resetPassword.getNewPassword());
		PasswordInfo passwordInfo = new PasswordInfo();
		passwordInfo.setType("password");
		passwordInfo.setValue(resetPassword.getNewPassword());
		passwordInfo.setTemporary(false);
		resetPasswordKeycloack(userDto.getIdKeycloak(), passwordInfo);

	}

	public void resetPasswordKeycloack(String id, PasswordInfo passwordInfo) throws APIErrorException {
		HttpHeaders headers = new HttpHeaders();
		adminLogin();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(AUTHORIZATION, bearer + accessTokenAdmin);
		HttpEntity<PasswordInfo> request = new HttpEntity<>(passwordInfo, headers);
		try {
			restTemplate.exchange(passwordReset + "/" + id + "/reset-password", HttpMethod.PUT, request, String.class)
					.getBody();
		} catch (Exception e) {
			throw new APIErrorException(ErrorCode.E444);
		}
	}

	@Override
	public void roleMappings(String id, List<RoleAssignment> roleAssignment) throws APIErrorException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(AUTHORIZATION, bearer + accessTokenAdmin);
		HttpEntity<List<RoleAssignment>> request = new HttpEntity<>(roleAssignment, headers);
		try {
			restTemplate.postForEntity(roleMapping + "/" + id + "/" + roleMappingClients, request, String.class)
					.getBody();
		} catch (Exception e) {
			throw new APIErrorException(ErrorCode.E444);
		}
	}

	@Override
	public Map<String, Object> findAll(int page, int size, String[] sort) throws APIErrorException {
		List<Order> orders = Utils.getListOrderBySort(sort);
		Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
		Page<User> usersPage = userRepository.findAll(pageable);

		if (usersPage.hasContent()) {
			List<UserDto> userDtos = userServiceMapper.convertListToListDto(usersPage.getContent(), UserDto.class);

			Map<String, Object> response = new HashMap<>();
			response.put("users", userDtos);
			response.put("currentPage", usersPage.getNumber());
			response.put("totalItems", usersPage.getTotalElements());
			response.put("totalPages", usersPage.getTotalPages());

			return response;
		} else {
			throw new APIErrorException(ErrorCode.A044);
		}
	}

	@Override
	public Map<String, Object> findAllByCompany(String uuid, int page, int size, String[] sort)
			throws APIErrorException {
		companyService.getCompanyByUuid(uuid);
		List<Order> orders = Utils.getListOrderBySort(sort);
		Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
		CompanyResponseDto companyResponseDto = companyService.getCompanyByUuid(uuid);
		Company company = mapClassWithResponseDto.convertToEntity(companyResponseDto, Company.class);
		Page<User> usersPage = userRepository.findAllByCompany(company, pageable);

		if (usersPage.hasContent()) {
			List<UserDto> userDtos = userServiceMapper.convertListToListDto(usersPage.getContent(), UserDto.class);

			Map<String, Object> response = new HashMap<>();
			response.put("users", userDtos);
			response.put("currentPage", usersPage.getNumber());
			response.put("totalItems", usersPage.getTotalElements());
			response.put("totalPages", usersPage.getTotalPages());

			return response;
		} else {
			throw new APIErrorException(ErrorCode.A044);
		}
	}

	@Override
	public Map<String, Object> findAllByProfile(String uuid, int page, int size, String[] sort)
			throws APIErrorException {
		List<Order> orders = Utils.getListOrderBySort(sort);
		Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
		Profile profile = profileRepository.findByUuid(uuid);
		Page<User> usersPage = userRepository.findAllByProfile(profile, pageable);

		if (usersPage.hasContent()) {
			List<UserDto> userDtos = userServiceMapper.convertListToListDto(usersPage.getContent(), UserDto.class);

			Map<String, Object> response = new HashMap<>();
			response.put("users", userDtos);
			response.put("currentPage", usersPage.getNumber());
			response.put("totalItems", usersPage.getTotalElements());
			response.put("totalPages", usersPage.getTotalPages());

			return response;
		} else {
			throw new APIErrorException(ErrorCode.A044);
		}
	}

	@Override
	public UserDto findByUuid(String uuid) throws APIErrorException {
		User user = userRepository.findByUuid(uuid);
		if (user == null) {
			throw new APIErrorException(ErrorCode.E45);
		} else {
			return userServiceMapper.convertToDto(user, UserDto.class);
		}
	}
	
	@Override
	public KeyDto demandeResetPassword(TrueResetPassword resetPassword) throws APIErrorException, MessagingException {
		
		User user = userRepository.findByUsername(resetPassword.getLogin().trim().toLowerCase());
		
		if (Objects.isNull(user)) {
			throw new  ApiKeyException(ErrorCode.A0401);
		}
		
		System.out.println(user.getUuid());
		System.out.println("oki");
		DemandeResetPassword demandeResetPassword = new DemandeResetPassword();
		demandeResetPassword.setUser(user);
		demandeResetPasswordRepository.save(demandeResetPassword);
		
    	System.out.println(demandeResetPassword.getUser().getUuid());

		
		EmailUser email = emailRepository.findByUserAndIsMain(user);
    	otpService.generateAndSendOTPEmail(email.getUuid());
    	
    	KeyDto keyDto = new KeyDto();
    	keyDto.setKey(demandeResetPassword.getUuid());
    	
    	System.out.println(demandeResetPassword.getUuid());
		return keyDto;
	}
	
	@Override
	public KeyTokenDto validerOTPResetPassword(ValiderOTPDto validerOTPDto) throws APIErrorException {
		
		DemandeResetPassword demandeResetPassword = demandeResetPasswordRepository.findByUuid(validerOTPDto.getKey());
		if (Objects.isNull(demandeResetPassword)) {
			throw new APIErrorException(ErrorCode.D0001);
		}
		User user = demandeResetPassword.getUser();
		
		EmailUser email = emailRepository.findByUserAndIsMain(user);

		validerOTPDto.setKey(email.getUuid());
    	otpService.validateOTPEmail(validerOTPDto);
    	
    	Token token = tokenService.generateTokenResetPasswordDemande(demandeResetPassword);
    	
    	return TokenKeyMapper.convertToDto(token, KeyTokenDto.class);
	}
	
	@Override
	public void confirmationResetPassword(ConfirmationResetPassword confirmationResetPassword) throws APIErrorException {

		if ( confirmationResetPassword.getPassword() == null && !confirmationResetPassword.getPassword().equals(confirmationResetPassword.getConfirmationPassword())) {
			throw new APIErrorException(ErrorCode.A0020);
		}
		Token token = tokenService.validateToken(confirmationResetPassword.getToken());
		
		DemandeResetPassword demandeResetPassword = token.getDemandeResetPassword();
		
		if (Objects.isNull(demandeResetPassword)) {
			throw new APIErrorException(ErrorCode.D0001);
		}
		User user = demandeResetPassword.getUser();
    	
    	resetPasswordKeycloak(user.getIdKeycloak(),confirmationResetPassword.getPassword());
		
    	demandeResetPassword.setValid(true);
    	demandeResetPasswordRepository.save(demandeResetPassword);
	}
	
	private TokenDto loginKeycloak(LoginDto loginDto) throws APIErrorException {

		ReponseTokenDto oReponseToken = null;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("username", loginDto.getUsername());
		map.add("password", loginDto.getPassword());
		map.add(CLIENT_ID, clientID);
		map.add(GRANT_TYPE, "password");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

		try {
			oReponseToken = restTemplate.postForEntity(keycloakTokenUri, request, ReponseTokenDto.class).getBody();
		} catch (Exception e) {	
		if ("401 Unauthorized: [no body]".equals(e.getMessage())) 
		{ throw new  ApiKeyException(ErrorCode.A0401); } 
		else throw new ApiKeyException(ErrorCode.E444);

		}
		return mapperToken.convertToEntity(oReponseToken, TokenDto.class);
	}
	
	@Override
	public void resetPasswordKeycloak(String idKeycloak, String password) throws APIErrorException {

		String UrlResetPassword = keycloakcreateUri + "/" + idKeycloak + "/reset-password";

		LoginDto login = new LoginDto();
		login.setUsername("admin");
		login.setPassword("3ichamohamed@22@ahmed");
		TokenDto token = loginKeycloak(login);

		Credential credential = new Credential();
		credential.setTemporary(false);
		credential.setType("password");
		credential.setValue(password);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "bearer " + token.getAccessToken());

		HttpEntity<Credential> requestEntity = new HttpEntity<>(credential, headers);

		try {
			restTemplate.exchange(UrlResetPassword, HttpMethod.PUT, requestEntity, Void.class);
		} catch (Exception e) {
			throw new ApiKeyException(ErrorCode.E444);
		}

		RefreshTocken rTokenDto = new RefreshTocken();
		rTokenDto.setRefresh_token(token.getRefreshToken());
		logout(rTokenDto);

	}
	

	

}
