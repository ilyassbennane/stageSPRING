
package com.ramadan.api.services.secteur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ramadan.api.dto.MapClassWithDto;
import com.ramadan.api.dto.address.model.LocationGPSRequestDto;
import com.ramadan.api.dto.address.model.LocationGPSSectorRequestDto;
import com.ramadan.api.dto.costumer.model.CostumerDto;
import com.ramadan.api.dto.costumer.model.CostumerFilterRequestDto;
import com.ramadan.api.dto.secteur.model.SecteurRequestDto;
import com.ramadan.api.dto.secteur.model.SecteurResponseDto;
import com.ramadan.api.dto.secteur.model.SecteurUpdateDto;
import com.ramadan.api.dto.secteur.model.SectorSearchFilterDto;
import com.ramadan.api.dto.secteur.service.ISecteurMapperService;
import com.ramadan.api.entity.agence.Agency;
import com.ramadan.api.entity.agence.LocationGPSSector;
import com.ramadan.api.entity.agence.Sector;
import com.ramadan.api.entity.costumer.Costumer;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ApiKeyException;
import com.ramadan.api.exceptions.ErrorCode;
import com.ramadan.api.helpers.Utils;
import com.ramadan.api.repository.agence.LocationGPSSectorRepository;
import com.ramadan.api.repository.agence.SecteurRepository;
import com.ramadan.api.services.agence.AgenceService;
import com.ramadan.api.services.costumer.CostumerSpecification;

@Service
@Transactional
public class SecteurService implements ISecteurService {

	@Autowired
	private SecteurRepository secteurRepository;

	@Autowired
	private AgenceService agenceService;

	@Autowired
	private ISecteurMapperService secteurMapperService;
	
	
	@Autowired
	private MapClassWithDto<Sector, SecteurRequestDto> mapClassWithRequestDto;

	@Autowired
	private MapClassWithDto<Sector, SecteurResponseDto> mapClassWithResponseDto;

	@Autowired
	LocationGPSSectorRepository locationGPSSectorRepository;

	@Override
	public void addLocationGpsToSector(String sectorUuid, List<LocationGPSSectorRequestDto> locations)
			throws APIErrorException {
		Sector sector = secteurRepository.findByUuid(sectorUuid);
		if (Objects.isNull(sector)) {
			throw new APIErrorException(ErrorCode.A519);
		}

		List<LocationGPSSector> existingLocations = locationGPSSectorRepository.findBySector(sector);
		int highestRang = existingLocations.stream().mapToInt(LocationGPSSector::getRang).max().orElse(0);

		int rang = highestRang + 1;
		for (LocationGPSSectorRequestDto locationDto : locations) {
			LocationGPSSector location = new LocationGPSSector();
			location.setSector(sector);
			location.setRang(rang++);
			location.setGpsLatitude(locationDto.getLatitude());
			location.setGpsLongitude(locationDto.getLongitude());
			locationGPSSectorRepository.save(location);
		}
	}

	@Override
	public SecteurResponseDto findByUuid(String uuid) throws APIErrorException {
		Sector secteur = secteurRepository.findByUuid(uuid);
		if (secteur != null) {
			return mapClassWithResponseDto.convertToDto(secteur, SecteurResponseDto.class);
		} else {
			throw new APIErrorException(ErrorCode.A519);
		}
	}

	@Override
	public SecteurResponseDto saveSecteur(SecteurRequestDto secteurRequestDto) throws APIErrorException {
		if (secteurRequestDto != null) {
			System.out.println(secteurRequestDto);
			Agency agence = agenceService.checkAgenceUuid(secteurRequestDto.getAgencyUuid());
			if (agence != null) {
				Sector secteur = mapClassWithRequestDto.convertToEntity(secteurRequestDto, Sector.class);
				secteur.setAgency(agence);
				Sector savedSecteur = secteurRepository.save(secteur);
				return mapClassWithResponseDto.convertToDto(savedSecteur, SecteurResponseDto.class);
			} else {
				throw new APIErrorException(ErrorCode.A001);
			}
		} else {
			throw new APIErrorException(ErrorCode.A507);
		}
	}

	@Override
	public SecteurResponseDto updateEntity(String uuid, SecteurUpdateDto requestDto) throws ApiKeyException {
		Sector existingEntity = secteurRepository.findByUuid(uuid);
		if (existingEntity != null) {

			if (requestDto.getDescription() != null && !requestDto.getDescription().isEmpty()) {
				existingEntity.setDescription(requestDto.getDescription());
			}
			if (requestDto.getRang() != null) {
				existingEntity.setRang(requestDto.getRang());
			}

			Sector updatedEntity = secteurRepository.save(existingEntity);
			return mapClassWithResponseDto.convertToDto(updatedEntity, SecteurResponseDto.class);
		} else
			throw new ApiKeyException(ErrorCode.Se001);
	}

	@Override
	public void deleteSecteur(String uuid) throws APIErrorException {
		Sector secteur = checkSectorUuid(uuid);
		if (Objects.nonNull(secteur)) {
			List<LocationGPSSector> locations = locationGPSSectorRepository.findBySector(secteur);
			for (LocationGPSSector location : locations) {
				location.setDelete(true);
				locationGPSSectorRepository.save(location);
			}
			secteur.setDelete(true);
			secteurRepository.save(secteur);
		} else {
			throw new APIErrorException(ErrorCode.A519);
		}
	}

	@Override
	public Sector checkSectorUuid(String uuid) throws APIErrorException {
		if (uuid == null) {
			throw new APIErrorException(ErrorCode.A520);
		}
		Sector oSector = secteurRepository.findByUuid(uuid);
		if (Objects.isNull(oSector)) {
			throw new APIErrorException(ErrorCode.A519);
		}

		return oSector;
	}

	@Override
	public Map<String, Object> search(SectorSearchFilterDto searchRequestDto, int page, int size, String[] sort)
			throws APIErrorException {

		List<Order> orders = Utils.getListOrderBySort(sort);

		List<Sector> lSectors= new ArrayList<Sector>();

		Pageable oPageable = PageRequest.of(page, size, Sort.by(orders));

		Page<Sector> pSectors;

		if (Objects.nonNull(searchRequestDto)) {
			SecteurSpecification specification = new SecteurSpecification(searchRequestDto);
			pSectors = secteurRepository.findAll(specification, oPageable);

		} else
			pSectors = secteurRepository.findAll(oPageable);

		lSectors = pSectors.getContent();
		List<SecteurResponseDto> lSectorDto = secteurMapperService.convertListToListDto(lSectors);

		Map<String, Object> lSectorsMap = new HashMap<>();
		lSectorsMap.put("result", lSectorDto);
		lSectorsMap.put("currentPage", pSectors.getNumber());
		lSectorsMap.put("totalItems", pSectors.getTotalElements());
		lSectorsMap.put("totalPages", pSectors.getTotalPages());

		return lSectorsMap;
	}
	@Override
	public Map<String, Object> findAllByAgency(String uuid, int page, int size, String[] sort)
			throws APIErrorException {
		System.out.println("Received request to find all sectors for agency UUID: " + uuid);

		Agency agence = agenceService.checkAgenceUuid(uuid);
		if (agence == null) {
			throw new APIErrorException(ErrorCode.A001);
		} else {
			List<Order> orders = Utils.getListOrderBySort(sort);
			Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
			Page<Sector> secteur = secteurRepository.findAllByAgency(uuid, pageable);

			if (secteur.hasContent()) {
				List<SecteurResponseDto> sectorList = secteur.getContent().stream()
						.map(sector -> mapClassWithResponseDto.convertToDto(sector, SecteurResponseDto.class))
						.collect(Collectors.toList());

				Map<String, Object> response = new HashMap<>();
				response.put("secteurs", sectorList);
				response.put("currentPage", secteur.getNumber());
				response.put("totalItems", secteur.getTotalElements());
				response.put("totalPages", secteur.getTotalPages());

				return response;
			} else {
				throw new APIErrorException(ErrorCode.A511);
			}
		}
	}
}
