package com.ramadan.api.dto.user.Profile.services;


import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;

import com.ramadan.api.dto.user.Profile.model.ProfileResponseDto;
import com.ramadan.api.entity.user.profile.Profile;



public interface IProfileMapperService {
    Page<ProfileResponseDto> convertToResponseDto(Page<Profile> profiles);
    

	Profile convertDtoToEntity(ProfileResponseDto profileDto);

	ProfileResponseDto convertEntityToDto(Profile profile);
	
	List<ProfileResponseDto> convertListToListDto(final Collection<Profile> entityList);
    
}
