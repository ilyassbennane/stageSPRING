package com.ramadan.api.services.RelationDeviceUtilisateur;

import java.util.Map;

import com.ramadan.api.dto.device.relationdeviceutilisateur.RelationDeviceUtilisateurRequestDto;
import com.ramadan.api.dto.device.relationdeviceutilisateur.RelationDeviceUtilisateurResponseDto;
import com.ramadan.api.dto.device.relationdeviceutilisateur.RelationDeviceUtilisateurUpdateDto;
import com.ramadan.api.exceptions.APIErrorException;

public interface IRelationDeviceUtilisateurService {

    RelationDeviceUtilisateurResponseDto findByUuid(String uuid) throws APIErrorException;

    Map<String, Object> getAll(int page, int size, String[] sort) throws APIErrorException;

    Map<String, Object> getAllByUser(String userUuid, int page, int size, String[] sort) throws APIErrorException;

    Map<String, Object> getByDevice(String deviceUuid, int page, int size, String[] sort) throws APIErrorException;

    RelationDeviceUtilisateurResponseDto updateRelationDeviceUtilisateur(RelationDeviceUtilisateurUpdateDto requestDto) throws APIErrorException;

    void deleteRelationDeviceUtilisateur(String uuid) throws APIErrorException;

    RelationDeviceUtilisateurResponseDto addRelation(RelationDeviceUtilisateurRequestDto requestDto) throws APIErrorException;


    
    
}
