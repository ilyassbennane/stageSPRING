package com.ramadan.api.services.referentiel;

import java.util.List;
import java.util.Map;

import com.ramadan.api.dto.referentiel.CiviliteDto;
import com.ramadan.api.dto.referentiel.PaysDto;
import com.ramadan.api.dto.referentiel.VilleDto;

public interface IReferentielService {
	
	List<CiviliteDto> AllCivilite();
		
	Map<String, Object> AllNationnalite(int page, int size, String[] sort);
	
	List<VilleDto> AllVille();
	
	List<PaysDto> AllPays();
	
}
