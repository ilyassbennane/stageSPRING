package com.ramadan.api.services.agence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ramadan.api.config.TokenFilter;
import com.ramadan.api.dto.MapClassWithDto;
import com.ramadan.api.dto.address.model.LocationGPSRequestDto;
import com.ramadan.api.dto.agance.model.AddressAgencyRequestDto;
import com.ramadan.api.dto.agance.model.AddressAgencyResponseDto;
import com.ramadan.api.dto.agance.model.AddressAgencyUpdateDto;
import com.ramadan.api.dto.agance.model.AddressAgencyUpdateListDto;
import com.ramadan.api.dto.agance.model.AgenceFilterRequestDto;
import com.ramadan.api.dto.agance.model.AgenceRequestDto;
import com.ramadan.api.dto.agance.model.AgenceResponseDto;
import com.ramadan.api.dto.agance.model.AgenceUpdateDto;
import com.ramadan.api.dto.agance.model.LocationGPSUpdateAgencyDto;
import com.ramadan.api.dto.agance.model.PhoneAgencyRequestDto;
import com.ramadan.api.dto.agance.model.PhoneAgencyResponseDto;
import com.ramadan.api.dto.agance.model.PhoneAgencyUpdateDto;
import com.ramadan.api.dto.agance.model.PhoneAgencyUpdateListDto;
import com.ramadan.api.dto.agance.service.AddressAgencyMapperService;
import com.ramadan.api.dto.agance.service.AganceMapperService;
import com.ramadan.api.dto.agance.service.PhoneAgencyMapperService;
import com.ramadan.api.dto.costumer.model.CostumerDto;
import com.ramadan.api.dto.costumer.model.CostumerFilterRequestDto;
import com.ramadan.api.dto.user.model.UserKeycloak;
import com.ramadan.api.entity.agence.AdresseAgency;
import com.ramadan.api.entity.agence.Agency;
import com.ramadan.api.entity.agence.LocationGPSAdresseAgency;
import com.ramadan.api.entity.agence.PhoneAgency;
import com.ramadan.api.entity.company.Company;
import com.ramadan.api.entity.costumer.Costumer;
import com.ramadan.api.entity.user.User;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ApiKeyException;
import com.ramadan.api.exceptions.ErrorCode;
import com.ramadan.api.helpers.Utils;
import com.ramadan.api.repository.agence.AdresseAgencyRepository;
import com.ramadan.api.repository.agence.AgenceRepository;
import com.ramadan.api.repository.agence.LocationGPSAdresseAgencyRepository;
import com.ramadan.api.repository.agence.PhoneAgenceRepository;
import com.ramadan.api.repository.user.UserRepository;
import com.ramadan.api.services.company.CompanyService;
import com.ramadan.api.services.costumer.CostumerSpecification;
import com.ramadan.api.services.keycloak.IKeycloakService;

@Service
@Transactional
public class AgenceService implements IAgenceService {

	@Autowired
	private AgenceRepository agenceRepository;

	@Autowired
	IKeycloakService IkeycloakService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	AganceMapperService agenceMapperService;

	@Value("${max-telephones.number}")
	private int MaxTelephoneNumber;

	@Autowired
	private PhoneAgencyMapperService phoneAgencyMapper;

	@Autowired
	private PhoneAgenceRepository phoneAgenceRepository;

	@Autowired
	LocationGPSAdresseAgencyRepository locationGPSAdresseAgencyRepository;
//    @Autowired
//    private AdARepo agenceRepository;

	@Autowired
	private MapClassWithDto<Agency, AgenceRequestDto> agenceRequestMapper;

	@Autowired
	private MapClassWithDto<Agency, AgenceResponseDto> agenceResponseMapper;

	@Autowired
	private AddressAgencyMapperService addressAgencyMapperService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private AdresseAgencyRepository adresseAgencyRepository;

	@Override
	public AgenceResponseDto saveAgence(AgenceRequestDto agenceRequestDto) throws APIErrorException {
		if (Objects.isNull(agenceRequestDto)) {
			throw new APIErrorException(ErrorCode.A507);
		}

		if (agenceRequestDto.getAdresseAgencys() != null && !agenceRequestDto.getAdresseAgencys().isEmpty()) {
			boolean hasPrimaryAddress = false;
			for (AddressAgencyRequestDto addressDto : agenceRequestDto.getAdresseAgencys()) {
				if (addressDto.isMain()) {
					if (hasPrimaryAddress) {
						throw new APIErrorException(ErrorCode.PR00);
					}
					hasPrimaryAddress = true;
				}
				if (addressDto.getLocationGPSRequestDto() == null) {
					throw new APIErrorException(ErrorCode.A507);
				}
			}
			if (!hasPrimaryAddress) {
				throw new APIErrorException(ErrorCode.NPR0);
			}
		}

		if (agenceRequestDto.getPhonesCostumer() != null && !agenceRequestDto.getPhonesCostumer().isEmpty()) {
			if (agenceRequestDto.getPhonesCostumer().size() > MaxTelephoneNumber) {
				throw new APIErrorException(ErrorCode.P001);
			}
			boolean hasPrimaryPhone = false;
			for (PhoneAgencyRequestDto phoneDto : agenceRequestDto.getPhonesCostumer()) {
				if (phoneDto.isMain()) {
					if (hasPrimaryPhone) {
						throw new APIErrorException(ErrorCode.PR00);
					}
					hasPrimaryPhone = true;
				}
			}
			if (!hasPrimaryPhone) {
				throw new APIErrorException(ErrorCode.NPR0);
			}
		}

		User userConnected = IkeycloakService.getUserConnecter();
		String companyUUid = userConnected.getCompany().getUuid();

		Agency agency = new Agency();
		agency.setDescription(agenceRequestDto.getDescription());
		agency.setName(agenceRequestDto.getName());
		agency.setCapital(agenceRequestDto.getCapital());
		agency.setRegistreCommercial(agenceRequestDto.getRegistreCommercial());
		agency.setIdentifiantFiscal(agenceRequestDto.getIdentifiantFiscal());
		agency.setTravauxPublic(agenceRequestDto.getTravauxPublic());
		agency.setCodeVille(agenceRequestDto.getCodeVille());
		agency.setCnss(agenceRequestDto.getCnss());
		agency.setWeb(agenceRequestDto.getWeb());
		agency.setRang(agenceRequestDto.getRang());

		Company company = companyService.checkCompanyUuid(companyUUid);
		agency.setCompany(company);
		System.out.println("Before saving agency: " + agency);
		Agency savedAgency = agenceRepository.save(agency);
		System.out.println("After saving agency: " + savedAgency);

		if (agenceRequestDto.getAdresseAgencys() != null && !agenceRequestDto.getAdresseAgencys().isEmpty()) {
			for (AddressAgencyRequestDto addressDto : agenceRequestDto.getAdresseAgencys()) {
				AdresseAgency addressAgency = new AdresseAgency();
				addressAgency.setAgency(savedAgency);
				addressAgency.setMain(addressDto.isMain());
				addressAgency.setCodeVille(addressDto.getCodeVille());
				addressAgency.setAddress1(addressDto.getAddress1());
				addressAgency.setAddress2(addressDto.getAddress2());
				addressAgency.setQuartier(addressDto.getQuartier());
				addressAgency.setAddress3(addressDto.getAddress3());
				addressAgency.setCity(addressDto.getCity());
				addressAgency.setCountry(addressDto.getCountry());
				addressAgency.setZipCode(addressDto.getZipCode());

				AdresseAgency savedAddressAgency = adresseAgencyRepository.save(addressAgency);

				if (addressDto.getLocationGPSRequestDto() != null) {
					LocationGPSRequestDto locationGPSDto = addressDto.getLocationGPSRequestDto();
					LocationGPSAdresseAgency locationGPS = new LocationGPSAdresseAgency();
					locationGPS.setAdresseAgency(savedAddressAgency);
					locationGPS.setGpsLatitude(locationGPSDto.getLatitude());
					locationGPS.setGpsLongitude(locationGPSDto.getLongitude());

					locationGPSAdresseAgencyRepository.save(locationGPS);
					savedAddressAgency.setLocationGPS(locationGPS);
					adresseAgencyRepository.save(savedAddressAgency);
				}
			}
		}

		if (agenceRequestDto.getPhonesCostumer() != null && !agenceRequestDto.getPhonesCostumer().isEmpty()) {
			for (PhoneAgencyRequestDto phoneDto : agenceRequestDto.getPhonesCostumer()) {
				PhoneAgency phoneAgency = new PhoneAgency();
				phoneAgency.setAgency(savedAgency);
				phoneAgency.setMain(phoneDto.isMain());
				phoneAgency.setCodeTypePhone(phoneDto.getCodeTypePhone());
				phoneAgency.setPrefix(phoneDto.getPrefix());
				phoneAgency.setNumbre(phoneDto.getNumbre());

				phoneAgenceRepository.save(phoneAgency);
			}
		}

		AgenceResponseDto responseDto = new AgenceResponseDto();
		responseDto.setUuid(savedAgency.getUuid());

		return responseDto;
	}

	@Override
	public AgenceResponseDto addAddressToAgence(String agencyUuid, AddressAgencyRequestDto addressDto)
			throws APIErrorException {
		Agency agency = checkAgenceUuid(agencyUuid);

		// Get the list of addresses for the agency
		List<AdresseAgency> addresses = adresseAgencyRepository.findByAgency(agency);

		if (addressDto.isMain()) {
			for (AdresseAgency address : addresses) {
				if (address.isMain()) {
					throw new APIErrorException(ErrorCode.P0099);
				}
			}
		}

		// Create and save the new address entity
		AdresseAgency addressAgency = new AdresseAgency();
		addressAgency.setAgency(agency);
		addressAgency.setMain(addressDto.isMain());
		addressAgency.setCodeVille(addressDto.getCodeVille());
		addressAgency.setAddress1(addressDto.getAddress1());
		addressAgency.setAddress2(addressDto.getAddress2());
		addressAgency.setQuartier(addressDto.getQuartier());
		addressAgency.setAddress3(addressDto.getAddress3());
		addressAgency.setCity(addressDto.getCity());
		addressAgency.setCountry(addressDto.getCountry());
		addressAgency.setZipCode(addressDto.getZipCode());

		AdresseAgency savedAddressAgency = adresseAgencyRepository.save(addressAgency);

		if (addressDto.getLocationGPSRequestDto() != null) {
			LocationGPSRequestDto locationGPSDto = addressDto.getLocationGPSRequestDto();
			LocationGPSAdresseAgency locationGPS = new LocationGPSAdresseAgency();
			locationGPS.setAdresseAgency(savedAddressAgency);
			locationGPS.setGpsLatitude(locationGPSDto.getLatitude());
			locationGPS.setGpsLongitude(locationGPSDto.getLongitude());

			locationGPSAdresseAgencyRepository.save(locationGPS);
			savedAddressAgency.setLocationGPS(locationGPS);
			adresseAgencyRepository.save(savedAddressAgency);
		}

		AgenceResponseDto responseDto = new AgenceResponseDto();
		responseDto.setUuid(agency.getUuid());

		return responseDto;
	}

	@Override
	public AgenceResponseDto setMainAddress(String addressAgencyUuid) throws APIErrorException {
		AdresseAgency addressAgency = adresseAgencyRepository.findByUuid(addressAgencyUuid);
		if (Objects.isNull(addressAgency)) {
			throw new APIErrorException(ErrorCode.AD001);
		}
		Agency agency = addressAgency.getAgency();

		List<AdresseAgency> allAddresses = adresseAgencyRepository.findByAgency(agency);
		for (AdresseAgency addr : allAddresses) {
			addr.setMain(false);
		}
		adresseAgencyRepository.saveAll(allAddresses);

		addressAgency.setMain(true);
		AdresseAgency savedAddressAgency = adresseAgencyRepository.save(addressAgency);

		AgenceResponseDto responseDto = new AgenceResponseDto();
		responseDto.setUuid(agency.getUuid());

		return responseDto;
	}

	@Override
	public List<AddressAgencyResponseDto> getAllAddressAgencies(String agencyUuid) throws APIErrorException {
		Agency agency = agenceRepository.findByUuid(agencyUuid);
		if (Objects.isNull(agency)) {
			throw new APIErrorException(ErrorCode.AS065);
		}

		List<AdresseAgency> addressAgencies = adresseAgencyRepository.findByAgency(agency);
		System.out.println(addressAgencies.get(0).getLocationGPS().getGpsLatitude());
		return addressAgencyMapperService.convertListToListDto(addressAgencies);
	}

//    @Override
//    public AgenceResponseDto saveAgence(AgenceRequestDto agenceRequestDto) throws APIErrorException {
//        if (Objects.isNull(agenceRequestDto)) {
//            throw new APIErrorException(ErrorCode.A507);
//        }
//        Agency agence = agenceRequestMapper.convertToEntity(agenceRequestDto, Agency.class);
//
//        // Check if the agenceRequestDto has addresses
//        if (Objects.nonNull(agenceRequestDto.getAdresseAgencies()) && agenceRequestDto.getAdresseAgencies().size()>0) {
//            List<AdresseAgencyRequestDto> lAdresseAgencyRequestDto = agenceRequestDto.getAdresseAgencies();
//            
//    
//          List<AdresseAgency> lAdresseAgency =  adresseAgencyRequestMapper.convertListToListEntity(lAdresseAgencyRequestDto, AdresseAgency.class);
//			
//
//
//          lAdresseAgency.forEach(address -> address.setAgency(agence));
//
//
//          List<AdresseAgency> laAdresseAgencies = adresseAgencyRepository.saveAll(lAdresseAgency);
//
//
//          agence.setAdresseAgencies(laAdresseAgencies);
//           
//        }
//
//        Agency savedAgence = agenceRepository.save(agence);
//        return agenceResponseMapper.convertToDto(savedAgence, AgenceResponseDto.class);
//    }

	@Override
	public AgenceResponseDto findByUuid(String uuid) throws APIErrorException {
		Agency agence = agenceRepository.findByUuid(uuid);
		if (agence == null) {
			throw new APIErrorException(ErrorCode.A001);
		} else {
			return agenceResponseMapper.convertToDto(agence, AgenceResponseDto.class);
		}
	}

	@Override
	public Map<String, Object> findAllByCompany(String uuid, int page, int size, String[] sort)
			throws APIErrorException {
		Company company = companyService.checkCompanyUuid(uuid);

		if (company != null) {
			List<Sort.Order> orders = Utils.getListOrderBySort(sort);
			Pageable pageable = PageRequest.of(page, size, Sort.by(orders));

			Page<Agency> agenciesPage = agenceRepository.findAllByCompany(uuid, pageable);

			List<AgenceResponseDto> agencies = agenciesPage.getContent().stream()
					.map(agency -> agenceResponseMapper.convertToDto(agency, AgenceResponseDto.class))
					.collect(Collectors.toList());

			Map<String, Object> response = new HashMap<>();
			response.put("agencies", agencies);
			response.put("currentPage", agenciesPage.getNumber());
			response.put("totalItems", agenciesPage.getTotalElements());
			response.put("totalPages", agenciesPage.getTotalPages());

			return response;

		} else {
			throw new APIErrorException(ErrorCode.A010);
		}
	}

//    @Override
//    public AgenceResponseDto saveAgence(AgenceRequestDto agenceRequestDto) throws APIErrorException {
//        if (agenceRequestDto != null) {
//            Company company = companyService.checkCompanyUuid(agenceRequestDto.getCompanyUuid());
//            if (company==null){
//                throw new APIErrorException(ErrorCode.A0040);
//        }
//            else{Agency agence = mapClassWithRequestDto.convertToEntity(agenceRequestDto, Agency.class);
//                agence.setCompany(company);
//                Agency savedAgence = agenceRepository.save(agence);
//                return mapClassWithResponseDto.convertToDto(savedAgence, AgenceResponseDto.class);}
//        }
//        else {
//            throw new APIErrorException(ErrorCode.A507);
//        }
//    }

	@Override
	public AgenceResponseDto updateEntity(String uuid, AgenceUpdateDto requestDto) throws APIErrorException {
		Agency existingEntity = checkAgenceUuid(uuid);

		if (requestDto.getDescription() != null && !requestDto.getDescription().isEmpty()) {
			existingEntity.setDescription(requestDto.getDescription());
		}
		if (requestDto.getCnss() != null && !requestDto.getCnss().isEmpty()) {
			existingEntity.setCnss(requestDto.getCnss());
		}
		if (requestDto.getName() != null && !requestDto.getName().isEmpty()) {
			existingEntity.setName(requestDto.getName());
		}
		if (requestDto.getCapital() != null && !requestDto.getCapital().isEmpty()) {
			existingEntity.setCapital(requestDto.getCapital());
		}
//			if (requestDto.getFax() != null && !requestDto.getFax().isEmpty()) {
//				existingEntity.setFax(requestDto.getFax());
//			}
//			if (requestDto.getPattente() != null && !requestDto.getPattente().isEmpty()) {
//				existingEntity.setPattente(requestDto.getPattente());
//			}
		if (requestDto.getRang() != null) {
			existingEntity.setRang(requestDto.getRang());
		}
//			if (requestDto.getTelephone() != null && !requestDto.getTelephone().isEmpty()) {
//				existingEntity.setTelephone(requestDto.getTelephone());
//			}
		if (requestDto.getRegistreCommercial() != null && !requestDto.getRegistreCommercial().isEmpty()) {
			existingEntity.setRegistreCommercial(requestDto.getRegistreCommercial());
		}
		if (requestDto.getIdentifiantFiscal() != null && !requestDto.getIdentifiantFiscal().isEmpty()) {
			existingEntity.setIdentifiantFiscal(requestDto.getIdentifiantFiscal());
		}
		if (requestDto.getTravauxPublic() != null && !requestDto.getTravauxPublic().isEmpty()) {
			existingEntity.setTravauxPublic(requestDto.getTravauxPublic());
		}

		if (requestDto.getCodeVille() != null && !requestDto.getCodeVille().isEmpty()) {
			existingEntity.setCodeVille(requestDto.getCodeVille());
		}

		if (requestDto.getWeb() != null && !requestDto.getWeb().isEmpty()) {
			existingEntity.setWeb(requestDto.getWeb());
		}
		Agency updatedEntity = agenceRepository.save(existingEntity);
		return agenceResponseMapper.convertToDto(updatedEntity, AgenceResponseDto.class);
	}

	@Override
	public void deleteAgence(String uuid) throws APIErrorException {
		Agency agence = checkAgenceUuid(uuid);
		agence.setDelete(true);

		agenceRepository.save(agence);
		List<AdresseAgency> addressAgencies = adresseAgencyRepository.findByAgency(agence);
		List<PhoneAgency> phoneAgencies = phoneAgenceRepository.findByAgency(agence);

		for (AdresseAgency addressAgency : addressAgencies) {
			addressAgency.setDelete(true);
			adresseAgencyRepository.save(addressAgency);
		}
		for (PhoneAgency phoneAgency : phoneAgencies) {
			phoneAgency.setDelete(true);
			phoneAgenceRepository.save(phoneAgency);
		}
	}

	@Override
	public Map<String, Object> search(AgenceFilterRequestDto searchRequestDto, int page, int size, String[] sort)
			throws APIErrorException {

		User userConnected = IkeycloakService.getUserConnecter();
		String companyUuid = userConnected.getCompany().getUuid();
		Company company = companyService.checkCompanyUuid(companyUuid);
		List<Order> orders = Utils.getListOrderBySort(sort);
		Pageable pageable = PageRequest.of(page, size, Sort.by(orders));

		Page<Agency> pAgences;
		AgenceSpecification companySpecification = new AgenceSpecification(companyUuid);

		if (searchRequestDto != null) {
			AgenceSpecification searchSpecification = new AgenceSpecification(searchRequestDto);
			pAgences = agenceRepository.findAll(Specification.where(companySpecification).and(searchSpecification),
					pageable);
		} else {
			pAgences = agenceRepository.findAll(Specification.where(companySpecification), pageable);
		}

		List<Agency> lAgences = pAgences.getContent();
		List<AgenceResponseDto> lAgenceDto = lAgences.stream().map(agenceMapperService::convertEntityToDto)
				.collect(Collectors.toList());

		Map<String, Object> lAgencesMap = new HashMap<>();
		lAgencesMap.put("result", lAgenceDto);
		lAgencesMap.put("currentPage", pAgences.getNumber());
		lAgencesMap.put("totalItems", pAgences.getTotalElements());
		lAgencesMap.put("totalPages", pAgences.getTotalPages());

		return lAgencesMap;
	}

	@Override
	public Agency checkAgenceUuid(String uuid) throws APIErrorException {
		if (uuid == null || uuid.trim().isEmpty()) {
			throw new APIErrorException(ErrorCode.AS064);
		}
		Agency agency = agenceRepository.findByUuid(uuid);
		if (Objects.isNull(agency)) {
			throw new APIErrorException(ErrorCode.AS065);
		}

		return agency;
	}

	//////////////////////////////////////////////
	@Override
	public AgenceResponseDto addPhoneToAgency(String agencyUUId, PhoneAgencyRequestDto phoneDto)
			throws APIErrorException {
		// Find the agency by UUID
		Agency agency = agenceRepository.findByUuid(agencyUUId);
		if (Objects.isNull(agency)) {
			throw new APIErrorException(ErrorCode.A001);
		}

		// Get the list of phones for the agency
		List<PhoneAgency> phones = phoneAgenceRepository.findByAgency(agency);

		// Check if the new phone is to be the main phone
		if (phoneDto.isMain()) {
			// Check if there is already a main phone in the list
			for (PhoneAgency phone : phones) {
				if (phone.isMain()) {
					throw new APIErrorException(ErrorCode.P0099);
				}
			}
		}

		// Check if the number of phones exceeds the maximum allowed
		if (phones.size() >= MaxTelephoneNumber) {
			throw new APIErrorException(ErrorCode.P001);
		}

		// Create and save the new phone entity
		PhoneAgency phoneAgency = new PhoneAgency();
		phoneAgency.setAgency(agency);
		phoneAgency.setMain(phoneDto.isMain());
		phoneAgency.setCodeTypePhone(phoneDto.getCodeTypePhone());
		phoneAgency.setPrefix(phoneDto.getPrefix());
		phoneAgency.setNumbre(phoneDto.getNumbre());
		PhoneAgency savedPhoneAgency = phoneAgenceRepository.save(phoneAgency);

		// Prepare and return the response DTO
		AgenceResponseDto responseDto = new AgenceResponseDto();
		responseDto.setUuid(agency.getUuid());

		return responseDto;
	}

	@Override
	public AgenceResponseDto setMainPhone(String phoneAgencyUuid) throws APIErrorException {
		PhoneAgency phoneAgency = phoneAgenceRepository.findByUuid(phoneAgencyUuid);
		if (Objects.isNull(phoneAgency)) {
			throw new APIErrorException(ErrorCode.P002);
		}
		Agency agence = phoneAgency.getAgency();

		List<PhoneAgency> allPhones = phoneAgenceRepository.findByAgency(agence);
		for (PhoneAgency phone : allPhones) {
			phone.setMain(false);
		}
		phoneAgenceRepository.saveAll(allPhones);

		phoneAgency.setMain(true);
		PhoneAgency savedPhoneCostumer = phoneAgenceRepository.save(phoneAgency);

		AgenceResponseDto responseDto = new AgenceResponseDto();
		responseDto.setUuid(agence.getUuid());

		return responseDto;
	}

	@Override
	public List<PhoneAgencyResponseDto> getAllPhonesAgency(String agencyUuid) throws APIErrorException {
		Agency agence = agenceRepository.findByUuid(agencyUuid);
		if (Objects.isNull(agence)) {
			throw new APIErrorException(ErrorCode.A006);
		}

		List<PhoneAgency> phoneAgence = phoneAgenceRepository.findByAgency(agence);

		return phoneAgencyMapper.convertListToListDto(phoneAgence);
	}

	@Override
	public void updatePhonesForAgency(PhoneAgencyUpdateListDto phoneUpdateListDto) throws APIErrorException {
		String agencyUuid = phoneUpdateListDto.getUuid();

		Agency agency = checkAgenceUuid(agencyUuid);

		List<PhoneAgency> phoneAgencies = phoneAgenceRepository.findByAgency(agency);
		if (phoneAgencies == null || phoneAgencies.isEmpty()) {
			throw new APIErrorException(ErrorCode.P001B);
		}

		for (PhoneAgencyUpdateDto phoneUpdateDto : phoneUpdateListDto.getPhones()) {
			String phoneUuid = phoneUpdateDto.getUuid();

			PhoneAgency phoneAgency = phoneAgenceRepository.findByUuid(phoneUuid);
			if (phoneAgency == null) {
				throw new APIErrorException(ErrorCode.P001A);
			}

			phoneAgency.setCodeTypePhone(phoneUpdateDto.getCodeTypePhone());
			phoneAgency.setPrefix(phoneUpdateDto.getPrefix());
			phoneAgency.setNumbre(phoneUpdateDto.getNumbre());

			phoneAgenceRepository.save(phoneAgency);
		}
	}

	@Override
	public void updateAddressesForAgency(AddressAgencyUpdateListDto addressUpdateListDto) throws APIErrorException {
		String agencyUuid = addressUpdateListDto.getUuid();

		Agency agency = checkAgenceUuid(agencyUuid);

		// Find the addresses associated with the agency
		List<AdresseAgency> addressAgencies = adresseAgencyRepository.findByAgency(agency);
		if (addressAgencies == null || addressAgencies.isEmpty()) {
			throw new APIErrorException(ErrorCode.AS001B);
		}

		// Iterate through the address updates
		for (AddressAgencyUpdateDto addressUpdateDto : addressUpdateListDto.getAddresses()) {
			String addressUuid = addressUpdateDto.getUuid();

			// Find the address by its UUID
			AdresseAgency addressAgency = adresseAgencyRepository.findByUuid(addressUuid);
			if (addressAgency == null) {
				throw new APIErrorException(ErrorCode.AD001);
			}

			// Update the address fields
			addressAgency.setCodeVille(addressUpdateDto.getCodeVille());
			addressAgency.setAddress1(addressUpdateDto.getAddress1());
			addressAgency.setAddress2(addressUpdateDto.getAddress2());
			addressAgency.setQuartier(addressUpdateDto.getQuartier());
			addressAgency.setAddress3(addressUpdateDto.getAddress3());
			addressAgency.setCity(addressUpdateDto.getCity());
			addressAgency.setCountry(addressUpdateDto.getCountry());
			addressAgency.setZipCode(addressUpdateDto.getZipCode());

			LocationGPSUpdateAgencyDto locationGPSUpdateDto = addressUpdateDto.getLocationGPSRequestDto();
			if (locationGPSUpdateDto != null) {
				LocationGPSAdresseAgency locationGPS = addressAgency.getLocationGPS();
				if (locationGPS == null) {
					locationGPS = new LocationGPSAdresseAgency();
					addressAgency.setLocationGPS(locationGPS);
				}
				locationGPS.setGpsLatitude(locationGPSUpdateDto.getLatitude());
				locationGPS.setGpsLongitude(locationGPSUpdateDto.getLongitude());
				locationGPSAdresseAgencyRepository.save(locationGPS);
			}

			adresseAgencyRepository.save(addressAgency);
		}
	}

}
