package com.ramadan.api.services.user.profile;

import java.util.Map;

import com.ramadan.api.dto.user.Profile.model.ProfileRequestDto;
import com.ramadan.api.dto.user.Profile.model.ProfileResponseDto;
import com.ramadan.api.dto.user.Profile.model.ProfileUpdateDto;
import com.ramadan.api.entity.user.profile.Profile;
import com.ramadan.api.exceptions.APIErrorException;

public interface IProfileService {
	ProfileResponseDto createProfile(ProfileRequestDto profileRequestDto) throws APIErrorException;
	
   Map<String, Object> findAll(int page, int size, String[] sort) throws APIErrorException;
	ProfileResponseDto updateProfile(ProfileUpdateDto profileUpdateDto) throws APIErrorException;
	    Profile checkProfileUuid(String uuid) throws APIErrorException;
	 
	    
	    Map<String, Object> findProfilesByCompany(String uuid,int page, int size,String[] sort) throws APIErrorException;
}
