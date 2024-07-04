package com.ramadan.api.services.mission;

import java.util.Map;

import com.ramadan.api.dto.mission.model.MissionRequestDto;
import com.ramadan.api.dto.mission.model.MissionResponseDto;
import com.ramadan.api.dto.mission.model.MissionUpdateDto;
import com.ramadan.api.exceptions.APIErrorException;

public interface IMissionService {
    MissionResponseDto findByUuid(String uuid) throws APIErrorException;

    MissionResponseDto saveMission(MissionRequestDto missionRequestDto) throws APIErrorException;

    void assignMissionToUser(String missionUuid, String userUuid) throws APIErrorException;

    // Method to assign a mission to a customer
    void assignMissionToCustomer(String missionUuid, String customerUuid) throws APIErrorException;

    // Method to assign a mission to an agency
    void assignMissionToAgency(String missionUuid, String agencyUuid) throws APIErrorException;

    MissionResponseDto updateEntity(String uuid, MissionUpdateDto requestDto);

    Map<String, Object> findAllByCompany(String uuid,int page, int size,String[] sort) throws APIErrorException;

    void deleteMission(String uuid) throws APIErrorException;

    Map<String, Object> findAll(int page, int size,String[] sort) throws APIErrorException ;

    Map<String, Object> findAllByClient(String clientUuid, int page, int size, String[] sort) throws APIErrorException;

    Map<String, Object> findAllByUser(String userUuid, int page, int size, String[] sort) throws APIErrorException;

    Map<String, Object> findAllByAgence(String agenceUuid, int page, int size, String[] sort) throws APIErrorException;
}
