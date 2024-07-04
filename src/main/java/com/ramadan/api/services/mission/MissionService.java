
package com.ramadan.api.services.mission;
import com.ramadan.api.dto.MapClassWithDto;

import com.ramadan.api.dto.mission.model.MissionRequestDto;
import com.ramadan.api.dto.mission.model.MissionResponseDto;
import com.ramadan.api.dto.mission.model.MissionUpdateDto;
import com.ramadan.api.entity.company.Company;
import com.ramadan.api.dto.mission.service.IMissionMapperService;
import com.ramadan.api.entity.agence.Agency;
import com.ramadan.api.entity.costumer.Costumer;
import com.ramadan.api.entity.tour.RelationTourAgency;
import com.ramadan.api.entity.tour.RelationTourCostumer;
import com.ramadan.api.entity.tour.RelationTourUser;

import com.ramadan.api.entity.tour.Tour;
import com.ramadan.api.entity.user.User;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ErrorCode;
import com.ramadan.api.helpers.Utils;
import com.ramadan.api.repository.agence.AgenceRepository;
import com.ramadan.api.repository.company.CompanyRepository;
import com.ramadan.api.repository.costumer.ClientRepository;
import com.ramadan.api.repository.tour.MissionRepository;
import com.ramadan.api.repository.tour.RelationTourAgencyRepository;
import com.ramadan.api.repository.tour.RelationTourCostumerRepository;
import com.ramadan.api.repository.tour.RelationTourUserRepository;
import com.ramadan.api.repository.user.UserRepository;
import com.ramadan.api.services.agence.AgenceService;
import com.ramadan.api.services.user.UserService;
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
public class MissionService implements IMissionService {
    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private MapClassWithDto<Tour, MissionRequestDto> mapClassWithRequestDto;

    @Autowired
    private MapClassWithDto<Tour, MissionResponseDto> mapClassWithResponseDto;

    @Autowired
    private RelationTourUserRepository relationTourUserRepository;

    @Autowired
    private RelationTourCostumerRepository relationTourCostumerRepository;

    @Autowired
    private RelationTourAgencyRepository relationTourAgencyRepository;
    @Autowired
    private AgenceRepository agenceRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
   private ClientRepository customerRepository;
    @Autowired
    private UserService userService;
//
//    @Autowired
//    private CostumerService costumerService;
    @Autowired
    private AgenceService agenceService;
    @Override
    public MissionResponseDto findByUuid(String uuid) throws APIErrorException {
        Tour mission = missionRepository.findByUuid(uuid);
        if (mission != null) {
            return mapClassWithResponseDto.convertToDto(mission, MissionResponseDto.class);
        } else {
            throw new APIErrorException(ErrorCode.A518);
        }
    }
    @Override
    public MissionResponseDto saveMission(MissionRequestDto missionRequestDto) throws APIErrorException {
        if (missionRequestDto == null) {
            throw new APIErrorException(ErrorCode.A507);
        }
        else{
            Company company = companyRepository.findByUuid(missionRequestDto.getCompanyUuid());
            if (company == null) {
                throw new APIErrorException(ErrorCode.A010);
            }
            else {
                Tour mission = mapClassWithRequestDto.convertToEntity(missionRequestDto, Tour.class);
                mission.setCompany(company);
                Tour savedMission = missionRepository.save(mission);
                return mapClassWithResponseDto.convertToDto(savedMission, MissionResponseDto.class);
            }
        }
    }

    @Override
    public Map<String, Object> findAllByCompany(String uuid,int page, int size,String[] sort) throws APIErrorException {
        Company company = companyRepository.findByUuid(uuid);
        if (company == null) {
            throw new APIErrorException(ErrorCode.A010);
        }
        else {
            List<Sort.Order> orders = Utils.getListOrderBySort(sort);
            Pageable pageable = PageRequest.of(page, size,Sort.by(orders));
        Page<Tour> missions = missionRepository.findAllByCompany(uuid,pageable);
        if (missions.hasContent()) {
            List<MissionResponseDto> missionList = missions.getContent().stream()
                    .map(tour -> mapClassWithResponseDto.convertToDto(tour, MissionResponseDto.class))
                    .collect(Collectors.toList());
    
            Map<String, Object> response = new HashMap<>();
            response.put("missions", missionList);
            response.put("currentPage", missions.getNumber());
            response.put("totalItems", missions.getTotalElements());
            response.put("totalPages", missions.getTotalPages());
    
            return response;
        } 
       else {
            throw new APIErrorException(ErrorCode.A511);
        }
    }
    }

    @Override
    public void assignMissionToUser(String missionUuid, String userUuid) throws APIErrorException {
        User user =userRepository.findByUuid(userUuid);
        Tour mission =missionRepository.findByUuid(missionUuid);
        if (user == null ||mission==null) {
            throw new APIErrorException(ErrorCode.E45);
        } else {
            RelationTourUser relation = new RelationTourUser();
            relation.setUser(user);
            relation.setTour(mission);
            relationTourUserRepository.save(relation);
        }
    }

    // Method to assign a mission to a customer
    @Override
    public void assignMissionToCustomer(String missionUuid, String customerUuid) throws APIErrorException {
        Costumer customer = customerRepository.findByUuid(customerUuid);
        Tour mission= missionRepository.findByUuid(missionUuid);
        if (customer==null||mission==null) {
            throw new APIErrorException(ErrorCode.E46);
        } else {
            RelationTourCostumer relation = new RelationTourCostumer();
            relation.setTour(mission);
            relation.setCostumer(customer);
           relationTourCostumerRepository.save(relation);
        }
    }

    // Method to assign a mission to an agency
    @Override
    public void assignMissionToAgency(String missionUuid, String agencyUuid) throws APIErrorException {
        Agency agency = agenceRepository.findByUuid(agencyUuid);
        Tour mission=missionRepository.findByUuid(missionUuid);
        if (agency==null||mission==null) {
            throw new APIErrorException(ErrorCode.E47);
        } else {
            RelationTourAgency relationTourAgency=new RelationTourAgency();
            relationTourAgency.setAgency(agency);
            relationTourAgency.setTour(mission);

            relationTourAgencyRepository.save(relationTourAgency);
        }
    }
    @Override
    public MissionResponseDto updateEntity(String uuid, MissionUpdateDto requestDto) {
        Tour existingEntity = missionRepository.findByUuid(uuid);
        if (existingEntity != null) {
            if (requestDto.getDescription() != null && !requestDto.getDescription().isEmpty()) {
                existingEntity.setDescription(requestDto.getDescription());
            }
            Tour updatedEntity = missionRepository.save(existingEntity);
            return mapClassWithResponseDto.convertToDto(updatedEntity, MissionResponseDto.class);
        }
        return null;
    }

    @Override
    public void deleteMission(String uuid) throws APIErrorException {
        Tour mission = missionRepository.findByUuid(uuid);
        if (mission != null) {
            mission.setDelete(true);
            missionRepository.save(mission);    
        } else {
            throw new APIErrorException(ErrorCode.A518);
        }
    }




    @Override
    public Map<String, Object> findAll(int page, int size,String[] sort) throws APIErrorException {
        List<Sort.Order> orders = Utils.getListOrderBySort(sort);
        Pageable pageable = PageRequest.of(page, size,Sort.by(orders));
        Page<Tour> missions = missionRepository.findAll(pageable);
            List<MissionResponseDto> missionList = missions.getContent().stream()
                    .map(tour -> mapClassWithResponseDto.convertToDto(tour, MissionResponseDto.class))
                    .collect(Collectors.toList());
    
            Map<String, Object> response = new HashMap<>();
            response.put("missions", missionList);
            response.put("currentPage", missions.getNumber());
            response.put("totalItems", missions.getTotalElements());
            response.put("totalPages", missions.getTotalPages());
    
            return response;
    }

//
//    @Override
//    public Map<String, Object> findAllByClient(String clientUuid, int page, int size, String[] sort) throws APIErrorException {
//        costumerService.findByUuid(clientUuid);
//        List<Sort.Order> orders = Utils.getListOrderBySort(sort);
//        Pageable pageable = PageRequest.of(page, size,Sort.by(orders));
//        Page<Tour> missions = relationTourCostumerRepository.getTourCostumer(clientUuid,pageable);
//
//            List<MissionResponseDto> missionList = missions.getContent().stream()
//                    .map(tour -> mapClassWithResponseDto.convertToDto(tour, MissionResponseDto.class))
//                    .collect(Collectors.toList());
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("missions", missionList);
//            response.put("currentPage", missions.getNumber());
//            response.put("totalItems", missions.getTotalElements());
//            response.put("totalPages", missions.getTotalPages());
//
//            return response;
//
//    }
    @Override
    public Map<String, Object> findAllByUser(String userUuid, int page, int size, String[] sort) throws APIErrorException {
        userService.findByUuid(userUuid);
        List<Sort.Order> orders = Utils.getListOrderBySort(sort);
        Pageable pageable = PageRequest.of(page, size,Sort.by(orders));
        Page<Tour> missions = relationTourUserRepository.getUserTours(userUuid, pageable);
            List<MissionResponseDto> missionList = missions.getContent().stream()
                    .map(tour -> mapClassWithResponseDto.convertToDto(tour, MissionResponseDto.class))
                    .collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("missions", missionList);
            response.put("currentPage", missions.getNumber());
            response.put("totalItems", missions.getTotalElements());
            response.put("totalPages", missions.getTotalPages());

            return response;

    }


    @Override
    public Map<String, Object> findAllByAgence(String agenceUuid, int page, int size, String[] sort) throws APIErrorException {
       agenceService.findByUuid(agenceUuid);
        List<Sort.Order> orders = Utils.getListOrderBySort(sort);
        Pageable pageable = PageRequest.of(page, size,Sort.by(orders));
        Page<Tour> missions = relationTourAgencyRepository.getAgenceTours(agenceUuid, pageable);
        List<MissionResponseDto> missionList = missions.getContent().stream()
                .map(tour -> mapClassWithResponseDto.convertToDto(tour, MissionResponseDto.class))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("missions", missionList);
        response.put("currentPage", missions.getNumber());
        response.put("totalItems", missions.getTotalElements());
        response.put("totalPages", missions.getTotalPages());

        return response;

    }
	@Override
	public Map<String, Object> findAllByClient(String clientUuid, int page, int size, String[] sort)
			throws APIErrorException {
		// TODO Auto-generated method stub
		return null;
	}



}
  

