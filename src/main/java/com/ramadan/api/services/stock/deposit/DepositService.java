package com.ramadan.api.services.stock.deposit;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ramadan.api.dto.address.model.LocationGPSRequestDto;
import com.ramadan.api.dto.agance.model.AddressAgencyRequestDto;
import com.ramadan.api.dto.agance.model.AgenceRequestDto;
import com.ramadan.api.dto.agance.model.AgenceResponseDto;
import com.ramadan.api.dto.agance.model.PhoneAgencyRequestDto;
import com.ramadan.api.entity.agence.AdresseAgency;
import com.ramadan.api.entity.agence.Agency;
import com.ramadan.api.entity.agence.LocationGPSAdresseAgency;
import com.ramadan.api.entity.agence.PhoneAgency;
import com.ramadan.api.entity.company.Company;
import com.ramadan.api.entity.user.User;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ErrorCode;
import com.ramadan.api.repository.stock.DepositRepository;
import com.ramadan.api.services.keycloak.IKeycloakService;

@Service
@Transactional
public class DepositService implements IDepositService{
	@Autowired
	private DepositRepository depositRepository;
	@Autowired
	IKeycloakService IkeycloakService;
	
	

//	@Override
//	public AgenceResponseDto createDeposit(AgenceRequestDto agenceRequestDto) throws APIErrorException {
//		if (Objects.isNull(agenceRequestDto)) {
//			throw new APIErrorException(ErrorCode.A507);
//		}
//
//		if (agenceRequestDto.getAdresseAgencys() != null && !agenceRequestDto.getAdresseAgencys().isEmpty()) {
//			boolean hasPrimaryAddress = false;
//			for (AddressAgencyRequestDto addressDto : agenceRequestDto.getAdresseAgencys()) {
//				if (addressDto.isMain()) {
//					if (hasPrimaryAddress) {
//						throw new APIErrorException(ErrorCode.PR00);
//					}
//					hasPrimaryAddress = true;
//				}
//				if (addressDto.getLocationGPSRequestDto() == null) {
//					throw new APIErrorException(ErrorCode.A507);
//				}
//			}
//			if (!hasPrimaryAddress) {
//				throw new APIErrorException(ErrorCode.NPR0);
//			}
//		}
//
//		if (agenceRequestDto.getPhonesCostumer() != null && !agenceRequestDto.getPhonesCostumer().isEmpty()) {
//			if (agenceRequestDto.getPhonesCostumer().size() > MaxTelephoneNumber) {
//				throw new APIErrorException(ErrorCode.P001);
//			}
//			boolean hasPrimaryPhone = false;
//			for (PhoneAgencyRequestDto phoneDto : agenceRequestDto.getPhonesCostumer()) {
//				if (phoneDto.isMain()) {
//					if (hasPrimaryPhone) {
//						throw new APIErrorException(ErrorCode.PR00);
//					}
//					hasPrimaryPhone = true;
//				}
//			}
//			if (!hasPrimaryPhone) {
//				throw new APIErrorException(ErrorCode.NPR0);
//			}
//		}
//
//		User userConnected = IkeycloakService.getUserConnecter();
//		String companyUUid = userConnected.getCompany().getUuid();
//
//
//		Agency agency = new Agency();
//		agency.setDescription(agenceRequestDto.getDescription());
//		agency.setName(agenceRequestDto.getName());
//		agency.setCapital(agenceRequestDto.getCapital());
//		agency.setRegistreCommercial(agenceRequestDto.getRegistreCommercial());
//		agency.setIdentifiantFiscal(agenceRequestDto.getIdentifiantFiscal());
//		agency.setTravauxPublic(agenceRequestDto.getTravauxPublic());
//		agency.setCodeVille(agenceRequestDto.getCodeVille());
//		agency.setCnss(agenceRequestDto.getCnss());
//		agency.setWeb(agenceRequestDto.getWeb());
//		agency.setRang(agenceRequestDto.getRang());
//
//		Company company = companyService.checkCompanyUuid(companyUUid);
//		agency.setCompany(company);
//	    System.out.println("Before saving agency: " + agency);
//		Agency savedAgency = agenceRepository.save(agency);
//	    System.out.println("After saving agency: " + savedAgency);
//
//		if (agenceRequestDto.getAdresseAgencys() != null && !agenceRequestDto.getAdresseAgencys().isEmpty()) {
//			for (AddressAgencyRequestDto addressDto : agenceRequestDto.getAdresseAgencys()) {
//				AdresseAgency addressAgency = new AdresseAgency();
//				addressAgency.setAgency(savedAgency);
//				addressAgency.setMain(addressDto.isMain());
//				addressAgency.setCodeVille(addressDto.getCodeVille());
//				addressAgency.setAddress1(addressDto.getAddress1());
//				addressAgency.setAddress2(addressDto.getAddress2());
//				addressAgency.setQuartier(addressDto.getQuartier());
//				addressAgency.setAddress3(addressDto.getAddress3());
//				addressAgency.setCity(addressDto.getCity());
//				addressAgency.setCountry(addressDto.getCountry());
//				addressAgency.setZipCode(addressDto.getZipCode());
//
//				AdresseAgency savedAddressAgency = adresseAgencyRepository.save(addressAgency);
//
//				if (addressDto.getLocationGPSRequestDto() != null) {
//					LocationGPSRequestDto locationGPSDto = addressDto.getLocationGPSRequestDto();
//					LocationGPSAdresseAgency locationGPS = new LocationGPSAdresseAgency();
//					locationGPS.setAdresseAgency(savedAddressAgency);
//					locationGPS.setGpsLatitude(locationGPSDto.getLatitude());
//					locationGPS.setGpsLongitude(locationGPSDto.getLongitude());
//
//					locationGPSAdresseAgencyRepository.save(locationGPS);
//					savedAddressAgency.setLocationGPS(locationGPS);
//					adresseAgencyRepository.save(savedAddressAgency);
//				}
//			}
//		}
//
//		if (agenceRequestDto.getPhonesCostumer() != null && !agenceRequestDto.getPhonesCostumer().isEmpty()) {
//			for (PhoneAgencyRequestDto phoneDto : agenceRequestDto.getPhonesCostumer()) {
//				PhoneAgency phoneAgency = new PhoneAgency();
//				phoneAgency.setAgency(savedAgency);
//				phoneAgency.setMain(phoneDto.isMain());
//				phoneAgency.setCodeTypePhone(phoneDto.getCodeTypePhone());
//				phoneAgency.setPrefix(phoneDto.getPrefix());
//				phoneAgency.setNumbre(phoneDto.getNumbre());
//
//				phoneAgenceRepository.save(phoneAgency);
//			}
//		}
//
//		AgenceResponseDto responseDto = new AgenceResponseDto();
//		responseDto.setUuid(savedAgency.getUuid());
//
//		return responseDto;
//	}
	

}
