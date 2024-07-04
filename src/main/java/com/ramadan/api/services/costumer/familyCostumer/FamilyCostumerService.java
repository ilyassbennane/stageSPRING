package com.ramadan.api.services.costumer.familyCostumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ramadan.api.dto.IMapClassWithDto;
import com.ramadan.api.dto.familyCostumer.model.FamilyCostumerDto;
import com.ramadan.api.dto.familyCostumer.model.FamilyCostumerRequestDto;
import com.ramadan.api.dto.familyCostumer.model.FamilyCostumerResponseDto;
import com.ramadan.api.dto.familyCostumer.model.FamilyCostumerSearchRequestDto;
import com.ramadan.api.dto.familyCostumer.model.FamilyCostumerUpdateDto;
import com.ramadan.api.dto.familyCostumer.services.IFamilyCostumerMapperService;
import com.ramadan.api.entity.company.Company;
import com.ramadan.api.entity.costumer.FamilyCostumer;
import com.ramadan.api.entity.user.User;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ErrorCode;
import com.ramadan.api.helpers.Utils;
import com.ramadan.api.repository.costumer.FamilyCostumerRepository;
import com.ramadan.api.services.company.ICompanyService;
import com.ramadan.api.services.keycloak.IKeycloakService;

@Service
@Transactional
public class FamilyCostumerService implements IFamilyCostumerService {
	
	@Autowired
	private IFamilyCostumerMapperService familyCostumerMapperService;

	@Autowired
	private FamilyCostumerRepository familyCostumerRepository;

	@Autowired
	private IMapClassWithDto<FamilyCostumer, FamilyCostumerRequestDto> mapFamilyCostumerRequestDto;

	@Autowired
	private IMapClassWithDto<FamilyCostumer, FamilyCostumerResponseDto> mapFamilyCostumerResponseDto;

	@Autowired
	private IMapClassWithDto<FamilyCostumer, FamilyCostumerDto> mapFamilyCostumerDto;

	@Autowired
	private ICompanyService companyService;

	@Autowired
	private IKeycloakService keycloakService;
	


	@Override
	public FamilyCostumerDto getFamilyCostumerByUuid(String uuid) throws APIErrorException {

		if (Objects.isNull(uuid)) {
			throw new APIErrorException(ErrorCode.F0067);
		}
		   FamilyCostumer oFamilyCostumer = checkFamilyCostumerUuid(uuid);
			return mapFamilyCostumerDto.convertToDto(oFamilyCostumer, FamilyCostumerDto.class);
		
	}
	@Override
	public List<FamilyCostumerResponseDto> getAllFamilyCostumer() throws APIErrorException {
		List<FamilyCostumer> familyCostumers = familyCostumerRepository.findAll();
		if (!familyCostumers.isEmpty()) {
			return mapFamilyCostumerResponseDto.convertListToListDto(familyCostumers, FamilyCostumerResponseDto.class);
		} else {
			throw new APIErrorException(ErrorCode.F511);
		}
	}

	@Override
	public FamilyCostumerResponseDto saveFamilyCostumer(FamilyCostumerRequestDto familyCostumerRequestDto)
			throws APIErrorException {

		if (Objects.isNull(familyCostumerRequestDto)) {
			throw new APIErrorException(ErrorCode.A507);
		} else {

			FamilyCostumer oFamilyCostumer = mapFamilyCostumerRequestDto.convertToEntity(familyCostumerRequestDto,
					FamilyCostumer.class);

			User userConnected = keycloakService.getUserConnecter();
			Company oCompany = userConnected.getCompany();

			if (Objects.isNull(oCompany)) {
				throw new APIErrorException(ErrorCode.A0040);
			}
			oFamilyCostumer.setCompany(oCompany);

			if (!Objects.isNull(familyCostumerRequestDto.getParentUuid())) {
				FamilyCostumer oFamilyParent = checkFamilyCostumerUuid(familyCostumerRequestDto.getParentUuid());
				
				oFamilyCostumer.setParent(oFamilyParent);
			}

			return mapFamilyCostumerResponseDto.convertToDto(familyCostumerRepository.save(oFamilyCostumer),
					FamilyCostumerResponseDto.class);

		}
	}

	@Override
	public FamilyCostumerResponseDto updateFamilyCostumer(FamilyCostumerUpdateDto familyCostumerUpdateDto)
			throws APIErrorException {

		if (Objects.isNull(familyCostumerUpdateDto)) {
			throw new APIErrorException(ErrorCode.A507);
		}else {
			FamilyCostumer oFamilyCostumer = checkFamilyCostumerUuid(familyCostumerUpdateDto.getUuid());
		

				if (!Objects.isNull(familyCostumerUpdateDto.getName())) {
					oFamilyCostumer.setName(familyCostumerUpdateDto.getName());
				}

				if (!Objects.isNull(familyCostumerUpdateDto.getDescription())) {
					oFamilyCostumer.setDescription(familyCostumerUpdateDto.getDescription());
				}

				if (!Objects.isNull(familyCostumerUpdateDto.getRang())) {
					oFamilyCostumer.setRang(familyCostumerUpdateDto.getRang());
				}

				if (!Objects.isNull(familyCostumerUpdateDto.getParentUuid())) {
					FamilyCostumer oFamilyParent = checkFamilyCostumerUuid(familyCostumerUpdateDto.getParentUuid());
					oFamilyCostumer.setParent(oFamilyParent);
				}

				return mapFamilyCostumerResponseDto.convertToDto(familyCostumerRepository.save(oFamilyCostumer),
						FamilyCostumerResponseDto.class);
		}
	
	}

	@Override
	public Map<String, Object> findAllByCompany(String uuid, int page, int size, String[] sort)
			throws APIErrorException {
		if (uuid == null) {
			throw new APIErrorException(ErrorCode.A0064);
		}
		Company company = companyService.checkCompanyUuid(uuid);
		if (Objects.isNull(company)) {
			throw new APIErrorException(ErrorCode.A0061);
		}
		List<Order> orders = Utils.getListOrderBySort(sort);
		Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
		Page<FamilyCostumer> familyCostumersPage = familyCostumerRepository.findAllByCompany(uuid, pageable);

		if (familyCostumersPage.hasContent()) {
			List<FamilyCostumerResponseDto> costumers = mapFamilyCostumerResponseDto
					.convertListToListDto(familyCostumersPage.getContent(), FamilyCostumerResponseDto.class);

			Map<String, Object> response = new HashMap<>();
			response.put("costumers", costumers);
			response.put("currentPage", familyCostumersPage.getNumber());
			response.put("totalItems", familyCostumersPage.getTotalElements());
			response.put("totalPages", familyCostumersPage.getTotalPages());

			return response;
		} else {
			throw new APIErrorException(ErrorCode.F512);
		}
	}

	@Override
	public Map<String, Object> findAllByParent(String uuid, int page, int size, String[] sort)
			throws APIErrorException {
		if (uuid == null) {
			throw new APIErrorException(ErrorCode.F0064);
		}
		FamilyCostumer familyParentCostumer = familyCostumerRepository.findParentByUuid(uuid);
		if (Objects.isNull(familyParentCostumer)) {
			throw new APIErrorException(ErrorCode.F0065);
		}
		List<Order> orders = Utils.getListOrderBySort(sort);
		Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
		Page<FamilyCostumer> familyCostumersPage = familyCostumerRepository.findAllByParent(uuid, pageable);
		if (familyCostumersPage.hasContent()) {
			List<FamilyCostumerResponseDto> costumers = mapFamilyCostumerResponseDto
					.convertListToListDto(familyCostumersPage.getContent(), FamilyCostumerResponseDto.class);

			Map<String, Object> response = new HashMap<>();
			response.put("costumers", costumers);
			response.put("currentPage", familyCostumersPage.getNumber());
			response.put("totalItems", familyCostumersPage.getTotalElements());
			response.put("totalPages", familyCostumersPage.getTotalPages());

			return response;
		} else {
			throw new APIErrorException(ErrorCode.F513);
		}
	}

	public Map<String, Object> findAll(int page, int size, String[] sort) throws APIErrorException {
		List<Order> orders = Utils.getListOrderBySort(sort);
		Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
		Page<FamilyCostumer> familyCostumersPage = familyCostumerRepository.findAll(pageable);

		if (familyCostumersPage.hasContent()) {
			List<FamilyCostumerResponseDto> costumers = mapFamilyCostumerResponseDto
					.convertListToListDto(familyCostumersPage.getContent(), FamilyCostumerResponseDto.class);

			Map<String, Object> response = new HashMap<>();
			response.put("costumers", costumers);
			response.put("currentPage", familyCostumersPage.getNumber());
			response.put("totalItems", familyCostumersPage.getTotalElements());
			response.put("totalPages", familyCostumersPage.getTotalPages());

			return response;
		} else {
			throw new APIErrorException(ErrorCode.A511);
		}
	}

	@Override
	public void deleteFamilyCostumer(String uuid) throws APIErrorException {
		FamilyCostumer oFamilyCostumer = checkFamilyCostumerUuid(uuid);
		if (Objects.nonNull(oFamilyCostumer)) {
			oFamilyCostumer.setDelete(true);
			familyCostumerRepository.save(oFamilyCostumer);
		} else {
			throw new APIErrorException(ErrorCode.A512);
		}
	}

	@Override
	public FamilyCostumer checkFamilyCostumerUuid(String uuid) throws APIErrorException {
		if (Objects.isNull(uuid) || uuid.trim().isEmpty()) {
			throw new APIErrorException(ErrorCode.F000);
		}
		FamilyCostumer familycostumer = familyCostumerRepository.findByUuid(uuid);
		if (Objects.isNull(familycostumer)) {
			throw new APIErrorException(ErrorCode.F002);
		}

		return familycostumer;
	}
	@Override
	public Map<String, Object> search(FamilyCostumerSearchRequestDto searchRequestDto, int page, int size, String[] sort)
			throws APIErrorException {

		
		List<Order> orders = Utils.getListOrderBySort(sort);

		List<FamilyCostumer> lFamilyCostumer = new ArrayList<FamilyCostumer>();

		Pageable oPageable = PageRequest.of(page, size, Sort.by(orders));

		Page<FamilyCostumer> pFamilyCostumer;

		if (Objects.nonNull(searchRequestDto)) {
			FamilyCostumerSpecification specification = new FamilyCostumerSpecification(searchRequestDto);
			pFamilyCostumer = familyCostumerRepository.findAll(specification,oPageable);

		}else pFamilyCostumer = familyCostumerRepository.findAll(oPageable);

		lFamilyCostumer = pFamilyCostumer.getContent();
		List<FamilyCostumerResponseDto> lFamilyCostumerDto = familyCostumerMapperService.convertListToListDto(lFamilyCostumer);

		Map<String, Object> lFamilyCostumerMap = new HashMap<>();
		lFamilyCostumerMap.put("result", lFamilyCostumerDto);
		lFamilyCostumerMap.put("currentPage", pFamilyCostumer.getNumber());
		lFamilyCostumerMap.put("totalItems", pFamilyCostumer.getTotalElements());
		lFamilyCostumerMap.put("totalPages", pFamilyCostumer.getTotalPages());

		return lFamilyCostumerMap;
	}

}
