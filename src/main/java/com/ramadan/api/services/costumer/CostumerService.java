package com.ramadan.api.services.costumer;

import java.util.ArrayList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import java.util.Arrays;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ramadan.api.dto.MapClassWithDto;
import com.ramadan.api.dto.address.model.LocationGPSRequestDto;
import com.ramadan.api.dto.agance.model.AddressAgencyUpdateDto;
import com.ramadan.api.dto.agance.model.LocationGPSUpdateAgencyDto;
import com.ramadan.api.dto.costumer.model.AddressCostumerRequestDto;
import com.ramadan.api.dto.costumer.model.AddressCostumerResponseDto;
import com.ramadan.api.dto.costumer.model.AddressCostumerUpdateListDto;
import com.ramadan.api.dto.costumer.model.CostumerDto;
import com.ramadan.api.dto.costumer.model.CostumerFilterRequestDto;
import com.ramadan.api.dto.costumer.model.CostumerRequestDto;
import com.ramadan.api.dto.costumer.model.CostumerUpdateDto;
import com.ramadan.api.dto.costumer.model.EmailCostumerRequestDto;
import com.ramadan.api.dto.costumer.model.EmailCostumerResponseDto;
import com.ramadan.api.dto.costumer.model.EmailCostumerUpdateDto;
import com.ramadan.api.dto.costumer.model.EmailCostumerUpdateListDto;
import com.ramadan.api.dto.costumer.model.PaymentMethodRequestDto;
import com.ramadan.api.dto.costumer.model.PhoneCostumerRequestDto;
import com.ramadan.api.dto.costumer.model.PhoneCostumerResponseDto;
import com.ramadan.api.dto.costumer.model.PhoneCostumerUpdateDto;
import com.ramadan.api.dto.costumer.model.PhoneCostumerUpdateListDto;
import com.ramadan.api.dto.costumer.service.AddressCostumerMapperService;
import com.ramadan.api.dto.costumer.service.EmailCostumerMapperService;
import com.ramadan.api.dto.costumer.service.ICostumerMapperService;
import com.ramadan.api.dto.costumer.service.PhoneCostumerMapperService;
import com.ramadan.api.entity.agence.Sector;
import com.ramadan.api.entity.costumer.AddressCostumer;
import com.ramadan.api.entity.costumer.Costumer;
import com.ramadan.api.entity.costumer.EmailCostumer;
import com.ramadan.api.entity.costumer.FamilyCostumer;
import com.ramadan.api.entity.costumer.LocationGPSAddressCostumer;
import com.ramadan.api.entity.costumer.PhoneCostumer;
import com.ramadan.api.entity.costumer.RelationCustomerPaymentMethod;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ApiKeyException;
import com.ramadan.api.exceptions.ErrorCode;
import com.ramadan.api.helpers.Utils;
import com.ramadan.api.repository.agence.SecteurRepository;
import com.ramadan.api.repository.costumer.AddressCostumerRepository;
import com.ramadan.api.repository.costumer.CostumerRepository;
import com.ramadan.api.repository.costumer.EmailCostumerRepository;
import com.ramadan.api.repository.costumer.LocationGPSAddressCostumerRepository;
import com.ramadan.api.repository.costumer.PhoneCostumerRepository;
import com.ramadan.api.repository.costumer.RelationCustomerPaymentMethodRepository;
import com.ramadan.api.services.costumer.familyCostumer.FamilyCostumerService;
import com.ramadan.api.services.secteur.SecteurService;

@Service
@Transactional
public class CostumerService implements ICostumerService {

	@Autowired
	private CostumerRepository customerRepository;
	@Autowired
	private SecteurService secteurService;

	@Autowired
	private ICostumerMapperService costumerMapperService;
	@Autowired
	private SecteurRepository secteurRepository;
	

	@Autowired
	private AddressCostumerRepository addressCostumerRepositroy;

	@Autowired
	private AddressCostumerMapperService addressCostumerMapper;

	@Autowired
	private PhoneCostumerMapperService phoneCostumerMapper;

	@Autowired
	private EmailCostumerMapperService emailCostumerMapper;

	@Autowired
	private MapClassWithDto<Costumer, CostumerDto> customerResponseMapper;

	@Autowired
	private RelationCustomerPaymentMethodRepository paymentMethodRepository;

	@Value("${max-telephones.number}")
	private int MaxTelephoneNumber;

	@Value("${max-emails.number}")
	private int MaxEmailNumber;

	@Value("${max-required.payment_method}")
	private int MaxPaymenMethod;

	@Autowired
	private LocationGPSAddressCostumerRepository locationGPSAdresseCostumerRepository;

	@Autowired
	private PhoneCostumerRepository phoneCostumerRepository;

	@Autowired
	private EmailCostumerRepository emailCostumerRepository;

	@Autowired
	private FamilyCostumerService familyCostumerService;

	// CreateCostumer
	@Override
	@Transactional
	public List<CostumerDto> saveCostumer(List<CostumerRequestDto> customerRequestDtos) throws APIErrorException {
		List<CostumerDto> savedCustomers = new ArrayList<>();

		for (CostumerRequestDto costumerRequestDto : customerRequestDtos) {
			Sector sector = secteurService.checkSectorUuid(costumerRequestDto.getSectorUuid());
			if (sector == null) {
				throw new APIErrorException(ErrorCode.A519);
			}
			FamilyCostumer familyCostumer = familyCostumerService
					.checkFamilyCostumerUuid(costumerRequestDto.getFamilyCustomerUuid());
			if (Objects.isNull(familyCostumer)) {
				throw new APIErrorException(ErrorCode.F002);
			}

			// Validate addresses if present
			if (costumerRequestDto.getAdressesCostumer() != null) {
				boolean hasPrimaryAddress = false;
				for (AddressCostumerRequestDto addressDto : costumerRequestDto.getAdressesCostumer()) {
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

			// Validate phones if present
			if (costumerRequestDto.getPhonesCostumer() != null) {
				if (costumerRequestDto.getPhonesCostumer().size() > MaxTelephoneNumber) {
					throw new APIErrorException(ErrorCode.P001);
				}
				boolean hasPrimaryPhone = false;
				for (PhoneCostumerRequestDto phoneDto : costumerRequestDto.getPhonesCostumer()) {
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

			// Validate emails if present
			if (costumerRequestDto.getEmailsCostumer() != null) {
				if (costumerRequestDto.getEmailsCostumer().size() > MaxEmailNumber) {
					throw new APIErrorException(ErrorCode.E001);
				}
				boolean hasPrimaryEmail = false;
				for (EmailCostumerRequestDto emailDto : costumerRequestDto.getEmailsCostumer()) {
					if (emailDto.isMain()) {
						if (hasPrimaryEmail) {
							throw new APIErrorException(ErrorCode.PR00);
						}
						hasPrimaryEmail = true;
					}
				}
				if (!hasPrimaryEmail) {
					throw new APIErrorException(ErrorCode.NPR0);
				}
			}

			if (costumerRequestDto.getPaymentMethods() == null || costumerRequestDto.getPaymentMethods().isEmpty()) {
				throw new APIErrorException(ErrorCode.A507);
			}

			Costumer customer = new Costumer();
			customer.setRaisonSociale1(costumerRequestDto.getRaisonSociale1());
			customer.setRaisonSociale2(costumerRequestDto.getRaisonSociale2());
			customer.setCodeTva(costumerRequestDto.getCodeTva());
			customer.setCodeGender(costumerRequestDto.getCodeGender());
			customer.setBarcode(costumerRequestDto.getBarcode());
			customer.setRang(costumerRequestDto.getRang());
			customer.setName(costumerRequestDto.getName());
			customer.setSector(sector);
			customer.setFamilyCostumer(familyCostumer);

			Costumer savedCostumer = customerRepository.save(customer);

			if (costumerRequestDto.getAdressesCostumer() != null) {
				for (AddressCostumerRequestDto addressDto : costumerRequestDto.getAdressesCostumer()) {
					AddressCostumer addressCostumer = new AddressCostumer();
					addressCostumer.setCostumer(savedCostumer);
					addressCostumer.setMain(addressDto.isMain());
					addressCostumer.setCodeVille(addressDto.getCodeVille());
					addressCostumer.setAdresse1(addressDto.getAddress1());
					addressCostumer.setAdresse2(addressDto.getAddress2());
					addressCostumer.setQuartier(addressDto.getQuartier());
					addressCostumer.setAdresse3(addressDto.getAddress3());
					addressCostumer.setCity(addressDto.getCity());
					addressCostumer.setCountry(addressDto.getCountry());
					addressCostumer.setZipCode(addressDto.getZipCode());

					AddressCostumer savedAddressCostumer = addressCostumerRepositroy.save(addressCostumer);

					if (addressDto.getLocationGPSRequestDto() != null) {
						LocationGPSRequestDto locationGPSDto = addressDto.getLocationGPSRequestDto();
						LocationGPSAddressCostumer locationGPS = new LocationGPSAddressCostumer();
						locationGPS.setAddressCostumer(savedAddressCostumer);
						locationGPS.setGpsLatitude(locationGPSDto.getLatitude());
						locationGPS.setGpsLongitude(locationGPSDto.getLongitude());

						locationGPSAdresseCostumerRepository.save(locationGPS);
						savedAddressCostumer.setLocationGPS(locationGPS);
						addressCostumerRepositroy.save(savedAddressCostumer);
					}
				}
			}

			if (costumerRequestDto.getPhonesCostumer() != null) {
				for (PhoneCostumerRequestDto phoneDto : costumerRequestDto.getPhonesCostumer()) {
					PhoneCostumer phoneCostumer = new PhoneCostumer();
					phoneCostumer.setCostumer(savedCostumer);
					phoneCostumer.setMain(phoneDto.isMain());
					phoneCostumer.setCodeTypePhone(phoneDto.getCodeTypePhone());
					phoneCostumer.setPrefix(phoneDto.getPrefix());
					phoneCostumer.setNumbre(phoneDto.getNumbre());

					phoneCostumerRepository.save(phoneCostumer);
				}
			}

			if (costumerRequestDto.getEmailsCostumer() != null) {
				for (EmailCostumerRequestDto emailDto : costumerRequestDto.getEmailsCostumer()) {
					EmailCostumer emailCostumer = new EmailCostumer();
					emailCostumer.setCostumer(savedCostumer);
					emailCostumer.setMain(emailDto.isMain());
					emailCostumer.setEmail(emailDto.getEmail());

					emailCostumerRepository.save(emailCostumer);
				}
			}

			for (PaymentMethodRequestDto methodDto : costumerRequestDto.getPaymentMethods()) {
				RelationCustomerPaymentMethod methodCostumer = new RelationCustomerPaymentMethod();
				methodCostumer.setCostumer(savedCostumer);
				methodCostumer.setCodePaymentMethod(methodDto.getPayment_method());

				paymentMethodRepository.save(methodCostumer);
			}

			CostumerDto responseDto = new CostumerDto();
			responseDto.setUuid(savedCostumer.getUuid());
			savedCustomers.add(responseDto);
		}

		return savedCustomers;
	}

	// FindCostumerByUUID
	@Override
	public CostumerDto findByUuid(String uuid) throws APIErrorException {
		Costumer costumer = customerRepository.findByUuid(uuid);
		if (costumer == null) {
			throw new APIErrorException(ErrorCode.C001);
		} else {
			return customerResponseMapper.convertToDto(costumer, CostumerDto.class);
		}
	}

	// GetAllCostumers
	@Override
	public Map<String, Object> search(CostumerFilterRequestDto searchRequestDto, int page, int size, String[] sort)
			throws APIErrorException {

		List<Order> orders = Utils.getListOrderBySort(sort);

		List<Costumer> lCostumers = new ArrayList<Costumer>();

		Pageable oPageable = PageRequest.of(page, size, Sort.by(orders));

		Page<Costumer> pCostumers;

		if (Objects.nonNull(searchRequestDto)) {
			CostumerSpecification specification = new CostumerSpecification(searchRequestDto);
			pCostumers = customerRepository.findAll(specification, oPageable);

		} else
			pCostumers = customerRepository.findAll(oPageable);

		lCostumers = pCostumers.getContent();
		List<CostumerDto> lCostumerDto = costumerMapperService.convertListToListDto(lCostumers);

		Map<String, Object> lCostumersMap = new HashMap<>();
		lCostumersMap.put("result", lCostumerDto);
		lCostumersMap.put("currentPage", pCostumers.getNumber());
		lCostumersMap.put("totalItems", pCostumers.getTotalElements());
		lCostumersMap.put("totalPages", pCostumers.getTotalPages());

		return lCostumersMap;
	}

	/////////
	@Override
	public Costumer checkCostumerUuid(String uuid) throws APIErrorException {
		if (uuid == null || uuid.trim().isEmpty()) {
			throw new APIErrorException(ErrorCode.AC099);
		}
		Costumer costumer = customerRepository.findByUuid(uuid);
		if (Objects.isNull(costumer)) {
			throw new APIErrorException(ErrorCode.C001);
		}

		return costumer;
	}

	// UpdateACostumer
	@Override
	public CostumerDto updateEntity(String uuid, CostumerUpdateDto requestDto) throws APIErrorException {
		Costumer existingEntity = checkCostumerUuid(uuid);
		if (requestDto.getName() != null && !requestDto.getName().isEmpty()) {
			existingEntity.setName(requestDto.getName());
		}
		if (requestDto.getRaisonSociale1() != null && !requestDto.getRaisonSociale1().isEmpty()) {
			existingEntity.setRaisonSociale1(requestDto.getRaisonSociale1());
		}
		if (requestDto.getRaisonSociale2() != null && !requestDto.getRaisonSociale2().isEmpty()) {
			existingEntity.setRaisonSociale2(requestDto.getRaisonSociale2());
		}
		if (requestDto.getCodeTva() != null && !requestDto.getCodeTva().isEmpty()) {
			existingEntity.setCodeTva(requestDto.getCodeTva());
		}
		if (requestDto.getCodeGender() != null && !requestDto.getCodeGender().isEmpty()) {
			existingEntity.setCodeGender(requestDto.getCodeGender());
		}
		if (requestDto.getBarcode() != null && !requestDto.getBarcode().isEmpty()) {
			existingEntity.setBarcode(requestDto.getBarcode());
		}
		if (requestDto.getRang() != null) {
			existingEntity.setRang(requestDto.getRang());
		}
		if (requestDto.getSectorUuid() != null && !requestDto.getSectorUuid().isEmpty()) {
			Sector sector = secteurService.checkSectorUuid(requestDto.getSectorUuid());
			if (sector != null) {
				existingEntity.setSector(sector);
			} else {
				throw new ApiKeyException(ErrorCode.Se001);
			}
		}
		if (requestDto.getFamilyCustomerUuid() != null && !requestDto.getFamilyCustomerUuid().isEmpty()) {
			FamilyCostumer familyCostumer = familyCostumerService
					.checkFamilyCostumerUuid(requestDto.getFamilyCustomerUuid());
			existingEntity.setFamilyCostumer(familyCostumer);
		}
		Costumer updatedEntity = customerRepository.save(existingEntity);
		return customerResponseMapper.convertToDto(updatedEntity, CostumerDto.class);
	}

	// DeleteCostumer
	@Override
	public void deleteCostumer(String uuid) throws APIErrorException {
		Costumer costumer = checkCostumerUuid(uuid);
		costumer.setDelete(true);

		customerRepository.save(costumer);
		List<AddressCostumer> addressCostumers = addressCostumerRepositroy.findByCostumer(costumer);
		List<PhoneCostumer> phoneCostumers = phoneCostumerRepository.findByCostumer(costumer);
		List<EmailCostumer> emailCostumers = emailCostumerRepository.findByCostumer(costumer);
		List<RelationCustomerPaymentMethod> paymentMethods = paymentMethodRepository.findByCostumer(costumer);
		for (AddressCostumer addressCostumer : addressCostumers) {
			addressCostumer.setDelete(true);
			addressCostumerRepositroy.save(addressCostumer);
		}
		for (PhoneCostumer phoneCostumer : phoneCostumers) {
			phoneCostumer.setDelete(true);
			phoneCostumerRepository.save(phoneCostumer);
		}
		for (EmailCostumer emailCostumer : emailCostumers) {
			emailCostumer.setDelete(true);
			emailCostumerRepository.save(emailCostumer);
		}
		for (RelationCustomerPaymentMethod paymentMethod : paymentMethods) {
			paymentMethod.setDelete(true);
			paymentMethodRepository.save(paymentMethod);
		}
	}

	//// AddressMethods
	@Override
	public CostumerDto addAddressToCostumer(String costumerUuid, AddressCostumerRequestDto addressDto)
			throws APIErrorException {
		// Find the customer by UUID
		Costumer customer = checkCostumerUuid(costumerUuid);

		// Get the list of addresses for the customer
		List<AddressCostumer> addresses = addressCostumerRepositroy.findByCostumer(customer);

		// Check if the new address is to be the main address
		if (addressDto.isMain()) {
			// Check if there is already a main address in the list
			for (AddressCostumer address : addresses) {
				if (address.isMain()) {
					throw new APIErrorException(ErrorCode.P0099);
				}
			}
		}

		// Create and save the new address entity
		AddressCostumer addressCostumer = new AddressCostumer();
		addressCostumer.setCostumer(customer);
		addressCostumer.setMain(addressDto.isMain());
		addressCostumer.setCodeVille(addressDto.getCodeVille());
		addressCostumer.setAdresse1(addressDto.getAddress1());
		addressCostumer.setAdresse2(addressDto.getAddress2());
		addressCostumer.setQuartier(addressDto.getQuartier());
		addressCostumer.setAdresse3(addressDto.getAddress3());
		addressCostumer.setCity(addressDto.getCity());
		addressCostumer.setCountry(addressDto.getCountry());
		addressCostumer.setZipCode(addressDto.getZipCode());

		AddressCostumer savedAddressCostumer = addressCostumerRepositroy.save(addressCostumer);

		// Check if there is a GPS location to be saved
		if (addressDto.getLocationGPSRequestDto() != null) {
			LocationGPSRequestDto locationGPSDto = addressDto.getLocationGPSRequestDto();
			LocationGPSAddressCostumer locationGPS = new LocationGPSAddressCostumer();
			locationGPS.setAddressCostumer(savedAddressCostumer);
			locationGPS.setGpsLatitude(locationGPSDto.getLatitude());
			locationGPS.setGpsLongitude(locationGPSDto.getLongitude());

			locationGPSAdresseCostumerRepository.save(locationGPS);
			savedAddressCostumer.setLocationGPS(locationGPS);
			addressCostumerRepositroy.save(savedAddressCostumer);
		}

		// Prepare and return the response DTO
		CostumerDto responseDto = new CostumerDto();
		responseDto.setUuid(customer.getUuid());

		return responseDto;
	}

	@Override
	public CostumerDto setMainAddress(String addressCostumerUuid) throws APIErrorException {
		AddressCostumer addressCustomer = addressCostumerRepositroy.findByUuid(addressCostumerUuid);
		if (Objects.isNull(addressCustomer)) {
			throw new APIErrorException(ErrorCode.AD001);
		}
		Costumer costumer = addressCustomer.getCostumer();

		List<AddressCostumer> allAddresses = addressCostumerRepositroy.findByCostumer(costumer);
		for (AddressCostumer addr : allAddresses) {
			addr.setMain(false);
		}
		addressCostumerRepositroy.saveAll(allAddresses);

		addressCustomer.setMain(true);
		AddressCostumer savedAddressCostumer = addressCostumerRepositroy.save(addressCustomer);

		CostumerDto responseDto = new CostumerDto();
		responseDto.setUuid(costumer.getUuid());

		return responseDto;
	}

	@Override
	public List<AddressCostumerResponseDto> getAllAddressCostumers(String customerUuid) throws APIErrorException {
		Costumer customer = checkCostumerUuid(customerUuid);
		List<AddressCostumer> addressCostumer = addressCostumerRepositroy.findByCostumer(customer);

		return addressCostumerMapper.convertListToListDto(addressCostumer);
	}

	/////////////////////////////

	//// PhoneMethods
	@Override
	public CostumerDto addPhoneToCostumer(String costumerUuid, PhoneCostumerRequestDto phoneDto)
			throws APIErrorException {
		// Find the customer by UUID
		Costumer customer = checkCostumerUuid(costumerUuid);

		// Get the list of phones for the customer
		List<PhoneCostumer> phones = phoneCostumerRepository.findByCostumer(customer);

		// Check if the new phone is to be the main phone
		if (phoneDto.isMain()) {
			// Check if there is already a main phone in the list
			for (PhoneCostumer phone : phones) {
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
		PhoneCostumer phoneCostumer = new PhoneCostumer();
		phoneCostumer.setCostumer(customer);
		phoneCostumer.setMain(phoneDto.isMain());
		phoneCostumer.setCodeTypePhone(phoneDto.getCodeTypePhone());
		phoneCostumer.setPrefix(phoneDto.getPrefix());
		phoneCostumer.setNumbre(phoneDto.getNumbre());
		PhoneCostumer savedPhoneCostumer = phoneCostumerRepository.save(phoneCostumer);

		// Prepare and return the response DTO
		CostumerDto responseDto = new CostumerDto();
		responseDto.setUuid(customer.getUuid());

		return responseDto;
	}

	@Override
	public CostumerDto setMainPhone(String phoneCostumerUuid) throws APIErrorException {
		PhoneCostumer phoneCostumer = phoneCostumerRepository.findByUuid(phoneCostumerUuid);
		if (Objects.isNull(phoneCostumer)) {
			throw new APIErrorException(ErrorCode.P002);
		}
		Costumer costumer = phoneCostumer.getCostumer();

		List<PhoneCostumer> allPhones = phoneCostumerRepository.findByCostumer(costumer);
		for (PhoneCostumer phone : allPhones) {
			phone.setMain(false);
		}
		phoneCostumerRepository.saveAll(allPhones);

		phoneCostumer.setMain(true);
		PhoneCostumer savedPhoneCostumer = phoneCostumerRepository.save(phoneCostumer);

		CostumerDto responseDto = new CostumerDto();
		responseDto.setUuid(costumer.getUuid());

		return responseDto;
	}

	@Override
	public List<PhoneCostumerResponseDto> getAllPhonesCostumer(String customerUuid) throws APIErrorException {
		Costumer customer = checkCostumerUuid(customerUuid);

		List<PhoneCostumer> phoneCostumer = phoneCostumerRepository.findByCostumer(customer);

		return phoneCostumerMapper.convertListToListDto(phoneCostumer);
	}

	/////////////////
	//////// EmailMethods
	@Override
	public CostumerDto addEmailToCostumer(String costumerUuid, EmailCostumerRequestDto emailDto)
			throws APIErrorException {
		// Find the customer by UUID
		Costumer customer = checkCostumerUuid(costumerUuid);

		// Get the list of emails for the customer
		List<EmailCostumer> emails = emailCostumerRepository.findByCostumer(customer);

		// Check if the new email is to be the main email
		if (emailDto.isMain()) {
			// Check if there is already a main email in the list
			for (EmailCostumer email : emails) {
				if (email.isMain()) {
					throw new APIErrorException(ErrorCode.P0099);
				}
			}
		}

		// Check if the number of emails exceeds the maximum allowed
		if (emails.size() >= MaxTelephoneNumber) {
			throw new APIErrorException(ErrorCode.E002);
		}

		// Create and save the new email entity
		EmailCostumer emailCostumer = new EmailCostumer();
		emailCostumer.setCostumer(customer);
		emailCostumer.setMain(emailDto.isMain());
		emailCostumer.setEmail(emailDto.getEmail());
		EmailCostumer savedEmailCostumer = emailCostumerRepository.save(emailCostumer);

		// Prepare and return the response DTO
		CostumerDto responseDto = new CostumerDto();
		responseDto.setUuid(customer.getUuid());

		return responseDto;
	}

	@Override
	public CostumerDto setMainEmail(String emailCostumerUuid) throws APIErrorException {
		EmailCostumer emailCostumer = emailCostumerRepository.findByUuid(emailCostumerUuid);
		if (Objects.isNull(emailCostumer)) {
			throw new APIErrorException(ErrorCode.E001);
		}
		Costumer costumer = emailCostumer.getCostumer();

		List<EmailCostumer> allEmails = emailCostumerRepository.findByCostumer(costumer);
		for (EmailCostumer email : allEmails) {
			email.setMain(false);
		}
		emailCostumerRepository.saveAll(allEmails);

		emailCostumer.setMain(true);
		EmailCostumer savedEmailCostumer = emailCostumerRepository.save(emailCostumer);

		CostumerDto responseDto = new CostumerDto();
		responseDto.setUuid(costumer.getUuid());

		return responseDto;
	}

	@Override
	public List<EmailCostumerResponseDto> getAllEmailsCostumer(String customerUuid) throws APIErrorException {
		Costumer customer = checkCostumerUuid(customerUuid);

		List<EmailCostumer> emailCostumer = emailCostumerRepository.findByCostumer(customer);

		return emailCostumerMapper.convertListToListDto(emailCostumer);
	}

	@Override
	public void updatePhonesForCostumer(PhoneCostumerUpdateListDto phoneUpdateListDto) throws APIErrorException {
		String costumerUuid = phoneUpdateListDto.getUuid();

		Costumer costumer = checkCostumerUuid(costumerUuid);
		List<PhoneCostumer> phoneCostumers = phoneCostumerRepository.findByCostumer(costumer);
		if (phoneCostumers == null || phoneCostumers.isEmpty()) {
			throw new APIErrorException(ErrorCode.P001C);
		}

		for (PhoneCostumerUpdateDto phoneUpdateDto : phoneUpdateListDto.getPhones()) {
			String phoneUuid = phoneUpdateDto.getUuid();

			PhoneCostumer phoneCostumer = phoneCostumerRepository.findByUuid(phoneUuid);
			if (phoneCostumer == null) {
				throw new APIErrorException(ErrorCode.P0002);
			}

			phoneCostumer.setCodeTypePhone(phoneUpdateDto.getCodeTypePhone());
			phoneCostumer.setPrefix(phoneUpdateDto.getPrefix());
			phoneCostumer.setNumbre(phoneUpdateDto.getNumbre());

			phoneCostumerRepository.save(phoneCostumer);
		}
	}

	@Override
	public void updateAddressesForCostumer(AddressCostumerUpdateListDto addressUpdateListDto) throws APIErrorException {
		String CostumerUuid = addressUpdateListDto.getUuid();

		Costumer costumer = checkCostumerUuid(CostumerUuid);

		// Find the addresses associated with the agency
		List<AddressCostumer> addressCostumers = addressCostumerRepositroy.findByCostumer(costumer);
		if (addressCostumers == null || addressCostumers.isEmpty()) {
			throw new APIErrorException(ErrorCode.AS0000);
		}

		// Iterate through the address updates
		for (AddressAgencyUpdateDto addressUpdateDto : addressUpdateListDto.getAddresses()) {
			String addressUuid = addressUpdateDto.getUuid();

			// Find the address by its UUID
			AddressCostumer addressCostumer = addressCostumerRepositroy.findByUuid(addressUuid);
			if (addressCostumer == null) {
				throw new APIErrorException(ErrorCode.AD001);
			}

			addressCostumer.setCodeVille(addressUpdateDto.getCodeVille());
			addressCostumer.setAdresse1(addressUpdateDto.getAddress1());
			addressCostumer.setAdresse2(addressUpdateDto.getAddress2());
			addressCostumer.setQuartier(addressUpdateDto.getQuartier());
			addressCostumer.setAdresse3(addressUpdateDto.getAddress3());
			addressCostumer.setCity(addressUpdateDto.getCity());
			addressCostumer.setCountry(addressUpdateDto.getCountry());
			addressCostumer.setZipCode(addressUpdateDto.getZipCode());

			LocationGPSUpdateAgencyDto locationGPSUpdateDto = addressUpdateDto.getLocationGPSRequestDto();
			if (locationGPSUpdateDto != null) {
				LocationGPSAddressCostumer locationGPS = addressCostumer.getLocationGPS();
				if (locationGPS == null) {
					locationGPS = new LocationGPSAddressCostumer();
					addressCostumer.setLocationGPS(locationGPS);
				}
				locationGPS.setGpsLatitude(locationGPSUpdateDto.getLatitude());
				locationGPS.setGpsLongitude(locationGPSUpdateDto.getLongitude());
				locationGPSAdresseCostumerRepository.save(locationGPS);
			}

			addressCostumerRepositroy.save(addressCostumer);
		}
	}

	@Override
	public void updateEmailsForCostumer(EmailCostumerUpdateListDto emailUpdateListDto) throws APIErrorException {
		String costumerUuid = emailUpdateListDto.getUuid();

		Costumer costumer = checkCostumerUuid(costumerUuid);
		List<EmailCostumer> emailCostumers = emailCostumerRepository.findByCostumer(costumer);
		if (emailCostumers == null || emailCostumers.isEmpty()) {
			throw new APIErrorException(ErrorCode.P001Cs);
		}

		for (EmailCostumerUpdateDto emailUpdateDto : emailUpdateListDto.getEmails()) {
			String emailUuid = emailUpdateDto.getUuid();

			EmailCostumer emailCostumer = emailCostumerRepository.findByUuid(emailUuid);
			if (emailCostumer == null) {
				throw new APIErrorException(ErrorCode.E0010);
			}

			emailCostumer.setEmail(emailUpdateDto.getEmail());

			emailCostumerRepository.save(emailCostumer);
		}
	}

	@Override
	public void deletePhonesForCostumer(List<String> phoneUuids) throws APIErrorException {
		for (String phoneUuid : phoneUuids) {
			PhoneCostumer phoneCostumer = phoneCostumerRepository.findByUuid(phoneUuid);
			if (phoneCostumer == null) {
				throw new APIErrorException(ErrorCode.P002);
			}
			phoneCostumer.setDelete(true);
			phoneCostumerRepository.save(phoneCostumer);
		}
	}

	@Override
	public void deleteAddressesForCostumer(List<String> addressUuids) throws APIErrorException {
		for (String addressUuid : addressUuids) {
			AddressCostumer addressCostumer = addressCostumerRepositroy.findByUuid(addressUuid);
			if (addressCostumer == null) {
				throw new APIErrorException(ErrorCode.AD001);
			}
			addressCostumer.setDelete(true);
			addressCostumerRepositroy.save(addressCostumer);
		}
	}

	@Override
	public void deleteEmailsForCostumer(List<String> emailUuids) throws APIErrorException {
		for (String emailUuid : emailUuids) {
			EmailCostumer emailCostumer = emailCostumerRepository.findByUuid(emailUuid);
			if (emailCostumer == null) {
				throw new APIErrorException(ErrorCode.E001);
			}
			emailCostumer.setDelete(true);
			emailCostumerRepository.save(emailCostumer);
		}
	}

//	@Override
//	public void updateAddressesForCostumer(AddressAgencyUpdateListDto addressUpdateListDto) throws APIErrorException {
//	    String agencyUuid = addressUpdateListDto.getUuid();
//
//	    Agency agency = checkAgenceUuid(agencyUuid);
//
//
//	    // Find the addresses associated with the agency
//	    List<AdresseAgency> addressAgencies = adresseAgencyRepository.findByAgency(agency);
//	    if (addressAgencies == null || addressAgencies.isEmpty()) {
//	        throw new APIErrorException(ErrorCode.AS001B);
//	    }
//
//	    // Iterate through the address updates
//	    for (AddressAgencyUpdateDto addressUpdateDto : addressUpdateListDto.getAddresses()) {
//	        String addressUuid = addressUpdateDto.getUuid();
//
//	        // Find the address by its UUID
//	        AdresseAgency addressAgency = adresseAgencyRepository.findByUuid(addressUuid);
//	        if (addressAgency == null) {
//	            throw new APIErrorException(ErrorCode.AD001);
//	        }
//
//	        // Update the address fields
//	        addressAgency.setCodeVille(addressUpdateDto.getCodeVille());
//	        addressAgency.setAddress1(addressUpdateDto.getAddress1());
//	        addressAgency.setAddress2(addressUpdateDto.getAddress2());
//	        addressAgency.setQuartier(addressUpdateDto.getQuartier());
//	        addressAgency.setAddress3(addressUpdateDto.getAddress3());
//	        addressAgency.setCity(addressUpdateDto.getCity());
//	        addressAgency.setCountry(addressUpdateDto.getCountry());
//	        addressAgency.setZipCode(addressUpdateDto.getZipCode());
//
//	        LocationGPSUpdateAgencyDto locationGPSUpdateDto = addressUpdateDto.getLocationGPSRequestDto();
//	        if (locationGPSUpdateDto != null) {
//	            LocationGPSAdresseAgency locationGPS = addressAgency.getLocationGPS();
//	            if (locationGPS == null) {
//	                locationGPS = new LocationGPSAdresseAgency();
//	                addressAgency.setLocationGPS(locationGPS);
//	            }
//	            locationGPS.setGpsLatitude(locationGPSUpdateDto.getLatitude());
//	            locationGPS.setGpsLongitude(locationGPSUpdateDto.getLongitude());
//	            locationGPSAdresseAgencyRepository.save(locationGPS);
//	        }
//
//	        adresseAgencyRepository.save(addressAgency);
//	    }
//	}
}
