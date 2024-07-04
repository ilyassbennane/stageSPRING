package com.ramadan.api.services.RelationDeviceUtilisateur;
import com.ramadan.api.dto.MapClassWithDto;
import com.ramadan.api.dto.device.relationdeviceutilisateur.RelationDeviceUtilisateurRequestDto;
import com.ramadan.api.dto.device.relationdeviceutilisateur.RelationDeviceUtilisateurResponseDto;
import com.ramadan.api.dto.device.relationdeviceutilisateur.RelationDeviceUtilisateurUpdateDto;
import com.ramadan.api.entity.device.Device;
import com.ramadan.api.entity.device.RelationDeviceUtilisateur;
import com.ramadan.api.entity.user.User;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ErrorCode;
import com.ramadan.api.helpers.Utils;
import com.ramadan.api.repository.device.DeviceRepository;
import com.ramadan.api.repository.device.RelationDeviceUtilisateurRepository;
import com.ramadan.api.repository.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class RelationDeviceUtilisateurService implements IRelationDeviceUtilisateurService {

    @Autowired
    private RelationDeviceUtilisateurRepository relationDeviceUtilisateurRepository;

    @Autowired
    private MapClassWithDto<RelationDeviceUtilisateur, RelationDeviceUtilisateurResponseDto> mapClassWithDto;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public RelationDeviceUtilisateurResponseDto findByUuid(String uuid) throws APIErrorException {
        RelationDeviceUtilisateur relationDeviceUtilisateur = relationDeviceUtilisateurRepository.findByUuid(uuid);
        if (relationDeviceUtilisateur == null) {
            throw new APIErrorException(ErrorCode.A507);
        } else {
            return mapClassWithDto.convertToDto(relationDeviceUtilisateur, RelationDeviceUtilisateurResponseDto.class);
        }
    }

    @Override
    public Map<String, Object> getAll(int page, int size, String[] sort) throws APIErrorException {
        List<Sort.Order> orders = Utils.getListOrderBySort(sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(orders));

        Page<RelationDeviceUtilisateur> relationDeviceUtilisateurPage = relationDeviceUtilisateurRepository.findAll(pageable);

        List<RelationDeviceUtilisateurResponseDto> dtos = relationDeviceUtilisateurPage.getContent().stream()
                .map(relation -> mapClassWithDto.convertToDto(relation, RelationDeviceUtilisateurResponseDto.class))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("relationDeviceUtilisateurs", dtos);
        response.put("currentPage", relationDeviceUtilisateurPage.getNumber());
        response.put("totalItems", relationDeviceUtilisateurPage.getTotalElements());
        response.put("totalPages", relationDeviceUtilisateurPage.getTotalPages());

        return response;
    }

    @Override
    public void deleteRelationDeviceUtilisateur(String uuid) throws APIErrorException {
        RelationDeviceUtilisateur relationDeviceUtilisateur = relationDeviceUtilisateurRepository.findByUuid(uuid);
        if (relationDeviceUtilisateur != null) {
            relationDeviceUtilisateurRepository.delete(relationDeviceUtilisateur);
        } else {
            throw new APIErrorException(ErrorCode.A507);
        }
    }

    @Override
public Map<String, Object> getAllByUser(String userUuid, int page, int size, String[] sort) throws APIErrorException {
    List<Sort.Order> orders = Utils.getListOrderBySort(sort);
    Pageable pageable = PageRequest.of(page, size, Sort.by(orders));

    Page<RelationDeviceUtilisateur> relationDeviceUtilisateurPage = relationDeviceUtilisateurRepository.findAllByUser(userUuid, pageable);

    List<RelationDeviceUtilisateurResponseDto> dtos = relationDeviceUtilisateurPage.getContent().stream()
            .map(relation -> mapClassWithDto.convertToDto(relation, RelationDeviceUtilisateurResponseDto.class))
            .collect(Collectors.toList());

    Map<String, Object> response = new HashMap<>();
    response.put("relationDeviceUtilisateurs", dtos);
    response.put("currentPage", relationDeviceUtilisateurPage.getNumber());
    response.put("totalItems", relationDeviceUtilisateurPage.getTotalElements());
    response.put("totalPages", relationDeviceUtilisateurPage.getTotalPages());

    return response;
}
@Override
public Map<String, Object> getByDevice(String deviceUuid, int page, int size, String[] sort) throws APIErrorException {
    List<Sort.Order> orders = Utils.getListOrderBySort(sort);
    Pageable pageable = PageRequest.of(page, size, Sort.by(orders));

    Page<RelationDeviceUtilisateur> relationDeviceUtilisateurPage = relationDeviceUtilisateurRepository.findByDevice(deviceUuid, pageable);

    List<RelationDeviceUtilisateurResponseDto> dtos = relationDeviceUtilisateurPage.getContent().stream()
            .map(relation -> mapClassWithDto.convertToDto(relation, RelationDeviceUtilisateurResponseDto.class))
            .collect(Collectors.toList());

    Map<String, Object> response = new HashMap<>();
    response.put("relationDeviceUtilisateurs", dtos);
    response.put("currentPage", relationDeviceUtilisateurPage.getNumber());
    response.put("totalItems", relationDeviceUtilisateurPage.getTotalElements());
    response.put("totalPages", relationDeviceUtilisateurPage.getTotalPages());

    return response;
}

@Override
public RelationDeviceUtilisateurResponseDto updateRelationDeviceUtilisateur( RelationDeviceUtilisateurUpdateDto requestDto) throws APIErrorException {
    RelationDeviceUtilisateur existingRelation = relationDeviceUtilisateurRepository.findByUuid(requestDto.getUuid());
    if (existingRelation == null) {
        throw new APIErrorException(ErrorCode.A507);
    } else {
        if (requestDto.getRang() != null) {
            existingRelation.setRang(requestDto.getRang());
        }
        if (requestDto.getCodeTypeAffection() != null) {
            existingRelation.setCodeTypeAffection(requestDto.getCodeTypeAffection());
        }
        if (requestDto.getDateAffectation() != null) {
            existingRelation.setDateAffectation(requestDto.getDateAffectation());
        }
        if (requestDto.getDateDebut() != null) {
            existingRelation.setDateDebut(requestDto.getDateDebut());
        }
        if (requestDto.getDateFin() != null) {
            existingRelation.setDateFin(requestDto.getDateFin());
        }
        RelationDeviceUtilisateur updatedRelation = relationDeviceUtilisateurRepository.save(existingRelation);
        return mapClassWithDto.convertToDto(updatedRelation, RelationDeviceUtilisateurResponseDto.class);
    }
}

@Override
public RelationDeviceUtilisateurResponseDto addRelation(RelationDeviceUtilisateurRequestDto requestDto) throws APIErrorException {
    // Check if the user exists
    User user = userRepository.findByUuid(requestDto.getUserUuid());
    if (user == null) {
        throw new APIErrorException(ErrorCode.A044);
    }
    
    // Check if the device exists
    Device device = deviceRepository.findByUuid(requestDto.getDeviceUuid());
    if (device == null) {
        throw new APIErrorException(ErrorCode.D001);
    }
    
    // Create a new relation
    RelationDeviceUtilisateur newRelation = new RelationDeviceUtilisateur();
    newRelation.setUser(user);
    newRelation.setDevice(device);
    newRelation.setRang(requestDto.getRang());
    newRelation.setCodeTypeAffection(requestDto.getCodeTypeAffection());
    newRelation.setDateAffectation(requestDto.getDateAffectation());
    newRelation.setDateDebut(requestDto.getDateDebut());
    newRelation.setDateFin(requestDto.getDateFin());
    
    // Save the new relation
    RelationDeviceUtilisateur savedRelation = relationDeviceUtilisateurRepository.save(newRelation);
    // Convert and return the response DTO
    return mapClassWithDto.convertToDto(savedRelation, RelationDeviceUtilisateurResponseDto.class);
}

}
