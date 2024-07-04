package com.ramadan.api.services.user.profile;

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
import com.ramadan.api.dto.MapClassWithDto;
import com.ramadan.api.dto.user.Profile.model.ProfileRequestDto;
import com.ramadan.api.dto.user.Profile.model.ProfileResponseDto;
import com.ramadan.api.dto.user.Profile.model.ProfileUpdateDto;
import com.ramadan.api.entity.company.Company;
import com.ramadan.api.entity.user.profile.Profile;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ApiKeyException;
import com.ramadan.api.exceptions.ErrorCode;
import com.ramadan.api.helpers.Utils;
import com.ramadan.api.repository.user.ProfileRepository;
import com.ramadan.api.services.company.CompanyService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class ProfileService implements IProfileService {
	
	
	private final ProfileRepository profileRepository;
    private final CompanyService companyService;
	
	 @Autowired
	    private MapClassWithDto<Profile, ProfileRequestDto> mapClassWithRequestDto;

	    @Autowired
	    private MapClassWithDto<Profile, ProfileResponseDto> mapClassWithResponseDto;

	@Override
	public ProfileResponseDto createProfile(ProfileRequestDto profileRequestDto) throws APIErrorException {
		
	      if (profileRequestDto != null) {
	    	  Company company = companyService.checkCompanyUuid(profileRequestDto.getCompanyUuid());
	            if (company==null){
	                throw new APIErrorException(ErrorCode.A0040);
	        }
	            else{
	            	Profile profile = mapClassWithRequestDto.convertToEntity(profileRequestDto, Profile.class);
	            	profile.setCompany(company);
	                Profile savedProfile = profileRepository.save(profile);
	                return mapClassWithResponseDto.convertToDto(savedProfile, ProfileResponseDto.class);}
	        }
	        else {
	            throw new APIErrorException(ErrorCode.A507);
	        }

	}
	
	   public Map<String, Object> findAll(int page, int size, String[] sort) throws APIErrorException {
		    List<Sort.Order> orders = Utils.getListOrderBySort(sort);
		    Pageable pageable = PageRequest.of(page, size,Sort.by(orders));
		    Page<Profile> profilePage = profileRepository.findAll(pageable);

		  
		        List<ProfileResponseDto> profiles = profilePage.getContent().stream()
		                .map(profile -> mapClassWithResponseDto.convertToDto(profile, ProfileResponseDto.class))
		                .collect(Collectors.toList());

		        Map<String, Object> response = new HashMap<>();
		        response.put("profiles", profiles);
		        response.put("currentPage", profilePage.getNumber());
		        response.put("totalItems", profilePage.getTotalElements());
		        response.put("totalPages", profilePage.getTotalPages());

		        return response;
		   }
	
	   
	   
	   @Override
		public ProfileResponseDto updateProfile(ProfileUpdateDto requestDto) throws APIErrorException {

		   Profile existingEntity = checkProfileUuid(requestDto.getUuid());
	        if (existingEntity != null) {
	         
	            if (requestDto.getName() != null && !requestDto.getName().isEmpty()) {
	                existingEntity.setName(requestDto.getName());
	            }
	            if (requestDto.getDescription() != null && !requestDto.getDescription().isEmpty()) {
	                existingEntity.setDescription(requestDto.getDescription());
	            }
	        


	            Profile updatedEntity = profileRepository.save(existingEntity);
	            return mapClassWithResponseDto.convertToDto(updatedEntity, ProfileResponseDto.class);
	        }
	        else  throw new ApiKeyException(ErrorCode.Se001) ;
		}
	

		@Override
	    public Profile checkProfileUuid(String uuid) throws APIErrorException {
		    if (uuid == null) {
	            throw new APIErrorException(ErrorCode.P0064);
	        }
		    Profile oprofile= profileRepository.findByUuid(uuid);
	        if (Objects.isNull(oprofile)) {
	            throw new APIErrorException(ErrorCode.P0061);
	        }
	        
	        return oprofile;
	    }
		
	    @Override
	    public Map<String, Object> findProfilesByCompany(String uuid,int page, int size,String[] sort) throws APIErrorException {
	        System.out.println("Received request to find all profiles for agency UUID: " + uuid);

	        Company company = companyService.checkCompanyUuid(uuid);	       
	        if (company == null) {
	            throw new APIErrorException(ErrorCode.A0040);
	        }else {
	            List<Order> orders = Utils.getListOrderBySort(sort);
	            Pageable pageable = PageRequest.of(page, size,Sort.by(orders));
	        Page<Profile> profiles = profileRepository.findAllByCompany(uuid,pageable);
	    
	        if (profiles.hasContent()) {
	            List<ProfileResponseDto> profileList = profiles.getContent().stream()
	                    .map(profil -> mapClassWithResponseDto.convertToDto(profil, ProfileResponseDto.class))
	                    .collect(Collectors.toList());
	    
	            Map<String, Object> response = new HashMap<>();
	            response.put("profiles", profileList);
	            response.put("currentPage", profiles.getNumber());
	            response.put("totalItems", profiles.getTotalElements());
	            response.put("totalPages", profiles.getTotalPages());
	    
	            return response;
	        }else {
	            throw new APIErrorException(ErrorCode.A511);     
	            }
	        }
	    }
		
	   
}
