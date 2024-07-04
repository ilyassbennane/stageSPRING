package com.ramadan.api.services.referentiel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.ramadan.api.dto.IMapClassWithDto;
import com.ramadan.api.dto.referentiel.CiviliteDto;
import com.ramadan.api.dto.referentiel.NationnaliteDto;
import com.ramadan.api.dto.referentiel.PaysDto;
import com.ramadan.api.dto.referentiel.VilleDto;
import com.ramadan.api.entity.referentiel.Civilite;
import com.ramadan.api.entity.referentiel.Nationalite;
import com.ramadan.api.entity.referentiel.Pays;
import com.ramadan.api.entity.referentiel.Ville;
import com.ramadan.api.helpers.Utils;
import com.ramadan.api.repository.referentiel.CiviliteRepository;
import com.ramadan.api.repository.referentiel.NationaliteRepository;
import com.ramadan.api.repository.referentiel.PaysRepository;
import com.ramadan.api.repository.referentiel.VilleRepository;


@Service
public class ReferentielService implements IReferentielService{

	
	@Autowired
	IMapClassWithDto<Civilite, CiviliteDto> civiliteMapperService;
	
	@Autowired
	IMapClassWithDto<Nationalite, NationnaliteDto> nationnaliteMapperService;
	
	@Autowired
	IMapClassWithDto<Ville, VilleDto> villeMapperService;
	
	@Autowired
	IMapClassWithDto<Pays, PaysDto> paysMapperService;
	
	@Autowired
	CiviliteRepository civiliteRepository;
	
	@Autowired
	NationaliteRepository nationnaliteRepository;
	
	@Autowired
	VilleRepository villeRepository;
		
	@Autowired
	PaysRepository paysRepository;
	

	
	@Override
	public List<CiviliteDto> AllCivilite() {
		
		List<Civilite> civilites = civiliteRepository.findAll();
		
		List<CiviliteDto> civiliteDtos = civiliteMapperService.convertListToListDto(civilites, CiviliteDto.class);
		
		return civiliteDtos;
	}

	@Override
	public  Map<String, Object> AllNationnalite(int page, int size, String[] sort) {
		
		List<Order> orders = Utils.getListOrderBySort(sort);
        List<Nationalite> nationalites = new ArrayList<Nationalite>();
        Pageable pageable = PageRequest.of(page, size, Sort.by(orders));

        Page<Nationalite> pNationalite;

        pNationalite = nationnaliteRepository.findAll(pageable);
     
        nationalites = pNationalite.getContent();
        
        List<NationnaliteDto> nationnaliteDtos = nationnaliteMapperService.convertListToListDto(nationalites, NationnaliteDto.class);

        Map<String, Object> nationaliteMap = new HashMap<>();
        nationaliteMap.put("tutorials", nationnaliteDtos);
        nationaliteMap.put("currentPage", pNationalite.getNumber());
        nationaliteMap.put("totalItems", pNationalite.getTotalElements());
        nationaliteMap.put("totalPages", pNationalite.getTotalPages());

        return nationaliteMap;
	}

	@Override
	public List<VilleDto> AllVille() {
		
		List<Ville> villes = villeRepository.findAll();
		
		List<VilleDto> villeDtos = villeMapperService.convertListToListDto(villes, VilleDto.class);
		
		return villeDtos;
	}
	
	@Override
	public List<PaysDto> AllPays() {
		
		List<Pays> pays = paysRepository.findAll();
		
		List<PaysDto> paysDtos = paysMapperService.convertListToListDto(pays, PaysDto.class);
		
		return paysDtos;
	}

}
