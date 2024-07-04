package com.ramadan.api.services.stock.deposit;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ramadan.api.dto.MapClassWithDto;
import com.ramadan.api.dto.address.model.LocationGPSRequestDto;
import com.ramadan.api.dto.agance.model.AddressAgencyRequestDto;
import com.ramadan.api.dto.agance.model.AddressAgencyResponseDto;
import com.ramadan.api.dto.agance.model.AgenceRequestDto;
import com.ramadan.api.dto.agance.model.AgenceResponseDto;
import com.ramadan.api.dto.agance.model.AgenceUpdateDto;
import com.ramadan.api.dto.agance.model.PhoneAgencyRequestDto;
import com.ramadan.api.dto.depot.model.AddressDepositRequestDto;
import com.ramadan.api.dto.depot.model.AddressDepositResponseDto;
import com.ramadan.api.dto.depot.model.DepotRequestDto;
import com.ramadan.api.dto.depot.model.DepotResponseDto;
import com.ramadan.api.dto.depot.model.DepotUpdateDto;
import com.ramadan.api.dto.depot.service.DepotMapperService;
import com.ramadan.api.dto.depot.service.IAddressDepositMapperService;
import com.ramadan.api.entity.agence.AdresseAgency;
import com.ramadan.api.entity.agence.Agency;
import com.ramadan.api.entity.agence.LocationGPSAdresseAgency;
import com.ramadan.api.entity.agence.PhoneAgency;
import com.ramadan.api.entity.company.Company;
import com.ramadan.api.entity.stock.deposit.AddressDeposit;
import com.ramadan.api.entity.stock.deposit.Deposit;
import com.ramadan.api.entity.stock.deposit.LocationGPSAddressDeposit;
import com.ramadan.api.entity.user.User;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ErrorCode;
import com.ramadan.api.repository.stock.AddressDepositRepository;
import com.ramadan.api.repository.stock.DepositRepository;
import com.ramadan.api.repository.stock.LocationGPSDepositRepository;
import com.ramadan.api.services.company.CompanyService;
import com.ramadan.api.services.keycloak.IKeycloakService;

@Service
@Transactional
public class DepositService implements IDepositService {
	@Autowired
	private DepositRepository depositRepository;
	@Autowired
	IKeycloakService IkeycloakService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private AddressDepositRepository addressDepositRepository;
	@Autowired
	private LocationGPSDepositRepository locationGPSDepositRepository;
	@Autowired
	private IAddressDepositMapperService addressDepositMapperService;
	@Autowired
	private MapClassWithDto<Deposit, DepotResponseDto> depositResponseMapper;

	@Override
	public DepotResponseDto createDeposit(DepotRequestDto depotRequestDto) throws APIErrorException {
		if (Objects.isNull(depotRequestDto)) {
			throw new APIErrorException(ErrorCode.A507);
		}

		if (depotRequestDto.getAdresseDeposit() != null && !depotRequestDto.getAdresseDeposit().isEmpty()) {
			boolean hasPrimaryAddress = false;
			for (AddressDepositRequestDto addressDto : depotRequestDto.getAdresseDeposit()) {
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

		User userConnected = IkeycloakService.getUserConnecter();
		String companyUUid = userConnected.getCompany().getUuid();

		Deposit depot = new Deposit();
		depot.setMatricule(depotRequestDto.getMatricule());
		depot.setVolume(depotRequestDto.getVolume());
		depot.setPoids(depotRequestDto.getPoids());
		depot.setCodeTypeDeposit(depotRequestDto.getCodeTypeDeposit());
		depot.setCodeUnitePoids(depotRequestDto.getCodeUnitePoids());
		depot.setTemperature_minimale(depotRequestDto.getTemperature_minimale());
		depot.setTemperature_maximale(depotRequestDto.getTemperature_maximale());
		depot.setRang(depotRequestDto.getRang());

		Company company = companyService.checkCompanyUuid(companyUUid);
		depot.setCompany(company);
		Deposit savedDeposit = depositRepository.save(depot);

		if (depotRequestDto.getAdresseDeposit() != null && !depotRequestDto.getAdresseDeposit().isEmpty()) {
			for (AddressDepositRequestDto addressDto : depotRequestDto.getAdresseDeposit()) {
				AddressDeposit addressDeposit = new AddressDeposit();
				addressDeposit.setDeposit(savedDeposit);
				addressDeposit.setMain(addressDto.isMain());
				addressDeposit.setCodeVille(addressDto.getCodeVille());
				addressDeposit.setAdresse1(addressDto.getAddress1());
				addressDeposit.setAdresse2(addressDto.getAddress2());
				addressDeposit.setQuartier(addressDto.getQuartier());
				addressDeposit.setAdresse3(addressDto.getAddress3());
				addressDeposit.setCity(addressDto.getCity());
				addressDeposit.setCountry(addressDto.getCountry());
				addressDeposit.setZipCode(addressDto.getZipCode());

				AddressDeposit savedAddressDepot = addressDepositRepository.save(addressDeposit);

				if (addressDto.getLocationGPSRequestDto() != null) {
					LocationGPSRequestDto locationGPSDto = addressDto.getLocationGPSRequestDto();
					LocationGPSAddressDeposit locationGPS = new LocationGPSAddressDeposit();
					locationGPS.setAdresseDeposit(savedAddressDepot);
					locationGPS.setGpsLatitude(locationGPSDto.getLatitude());
					locationGPS.setGpsLongitude(locationGPSDto.getLongitude());

					locationGPSDepositRepository.save(locationGPS);
					savedAddressDepot.setLocationGPS(locationGPS);
					addressDepositRepository.save(savedAddressDepot);
				}
			}
		}
		DepotResponseDto responseDto = new DepotResponseDto();
		responseDto.setUuid(savedDeposit.getUuid());

		return responseDto;
	}

	@Override
	public DepotResponseDto addAddressToDepot(String depotUuid, AddressDepositRequestDto addressDto)
			throws APIErrorException {
		Deposit depot = checkDepositUuid(depotUuid);

		List<AddressDeposit> addresses = addressDepositRepository.findByDeposit(depot);

		if (addressDto.isMain()) {
			for (AddressDeposit address : addresses) {
				if (address.isMain()) {
					throw new APIErrorException(ErrorCode.P0099);
				}
			}
		}

		AddressDeposit addressDeposit = new AddressDeposit();
		addressDeposit.setDeposit(depot);
		addressDeposit.setMain(addressDto.isMain());
		addressDeposit.setCodeVille(addressDto.getCodeVille());
		addressDeposit.setAdresse1(addressDto.getAddress1());
		addressDeposit.setAdresse2(addressDto.getAddress2());
		addressDeposit.setQuartier(addressDto.getQuartier());
		addressDeposit.setAdresse3(addressDto.getAddress3());
		addressDeposit.setCity(addressDto.getCity());
		addressDeposit.setCountry(addressDto.getCountry());
		addressDeposit.setZipCode(addressDto.getZipCode());

		AddressDeposit savedAddressDeposit = addressDepositRepository.save(addressDeposit);

		if (addressDto.getLocationGPSRequestDto() != null) {
			LocationGPSRequestDto locationGPSDto = addressDto.getLocationGPSRequestDto();
			LocationGPSAddressDeposit locationGPS = new LocationGPSAddressDeposit();
			locationGPS.setAdresseDeposit(savedAddressDeposit);
			locationGPS.setGpsLatitude(locationGPSDto.getLatitude());
			locationGPS.setGpsLongitude(locationGPSDto.getLongitude());

			locationGPSDepositRepository.save(locationGPS);
			savedAddressDeposit.setLocationGPS(locationGPS);
			addressDepositRepository.save(savedAddressDeposit);
		}

		DepotResponseDto responseDto = new DepotResponseDto();
		responseDto.setUuid(depot.getUuid());

		return responseDto;
	}

	@Override
	public DepotResponseDto setMainAddress(String addressDepositUuid) throws APIErrorException {
		AddressDeposit addressDeposit = addressDepositRepository.findByUuid(addressDepositUuid);
		if (Objects.isNull(addressDeposit)) {
			throw new APIErrorException(ErrorCode.AD001);
		}
		Deposit depot = addressDeposit.getDeposit();

		List<AddressDeposit> allAddresses = addressDepositRepository.findByDeposit(depot);
		for (AddressDeposit addr : allAddresses) {
			addr.setMain(false);
		}
		addressDepositRepository.saveAll(allAddresses);

		addressDeposit.setMain(true);
		AddressDeposit savedAddressAgency = addressDepositRepository.save(addressDeposit);

		DepotResponseDto responseDto = new DepotResponseDto();
		responseDto.setUuid(depot.getUuid());

		return responseDto;
	}

	
	@Override
	public List<AddressDepositResponseDto> getAllAddressDepot(String depotUuid) throws APIErrorException {
		Deposit depot = checkDepositUuid(depotUuid);

		List<AddressDeposit> addressDeposit = addressDepositRepository.findByDeposit(depot);
		return addressDepositMapperService.convertListToListDto(addressDeposit);
	}

	@Override
	public Deposit checkDepositUuid(String uuid) throws APIErrorException {
		if (uuid == null || uuid.trim().isEmpty()) {
			throw new APIErrorException(ErrorCode.ASSS);
		}
		Deposit depot = depositRepository.findByUuid(uuid);
		if (Objects.isNull(depot)) {
			throw new APIErrorException(ErrorCode.AS099);
		}

		return depot;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public DepotResponseDto updateEntity(String uuid, DepotUpdateDto requestDto) throws APIErrorException {
		Deposit existingEntity = checkDepositUuid(uuid);

		if (requestDto.getMatricule() != null && !requestDto.getMatricule().isEmpty()) {
		    existingEntity.setMatricule(requestDto.getMatricule());
		}

		if (requestDto.getVolume() != null) {
		    existingEntity.setVolume(requestDto.getVolume());
		}

		if (requestDto.getPoids() != null) {
		    existingEntity.setPoids(requestDto.getPoids());
		}

		if (requestDto.getCodeTypeDeposit() != null && !requestDto.getCodeTypeDeposit().isEmpty()) {
		    existingEntity.setCodeTypeDeposit(requestDto.getCodeTypeDeposit());
		}

		if (requestDto.getCodeUnitePoids() != null && !requestDto.getCodeUnitePoids().isEmpty()) {
		    existingEntity.setCodeUnitePoids(requestDto.getCodeUnitePoids());
		}

		if (requestDto.getCodeUniteVolume() != null && !requestDto.getCodeUniteVolume().isEmpty()) {
		    existingEntity.setCodeUniteVolume(requestDto.getCodeUniteVolume());
		}

		if (requestDto.getTemperature_minimale() != null) {
		    existingEntity.setTemperature_minimale(requestDto.getTemperature_minimale());
		}

		if (requestDto.getTemperature_maximale() != null) {
		    existingEntity.setTemperature_maximale(requestDto.getTemperature_maximale());
		}

		if (requestDto.getRang() != null) {
		    existingEntity.setRang(requestDto.getRang());
		}

		Deposit updatedEntity = depositRepository.save(existingEntity);
		return depositResponseMapper.convertToDto(updatedEntity, DepotResponseDto.class);
	}

	@Override
	public void deleteDeposit(String uuid) throws APIErrorException {
		Deposit depot = checkDepositUuid(uuid);
		depot.setDelete(true);

		depositRepository.save(depot);
		List<AddressDeposit> addressesDeposit = addressDepositRepository.findByDeposit(depot);

		for (AddressDeposit addressDeposit : addressesDeposit) {
			addressDeposit.setDelete(true);
			addressDepositRepository.save(addressDeposit);
		}

	}

}
