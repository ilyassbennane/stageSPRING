package com.ramadan.api.services.user.phone;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ramadan.api.dto.MapClassWithDto;
import com.ramadan.api.dto.user.PhoneUser.PhoneUserRequestDto;
import com.ramadan.api.dto.user.PhoneUser.PhoneUserResponseDto;
import com.ramadan.api.dto.user.PhoneUser.PhoneUserUpdateDto;
import com.ramadan.api.entity.user.PhoneUser;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ErrorCode;
import com.ramadan.api.repository.user.PhoneRepository;

@Service
@Transactional
public class PhoneUserService implements IPhoneUserService {

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private MapClassWithDto<PhoneUser, PhoneUserRequestDto> mapClassWithRequestDto;

    @Autowired
    private MapClassWithDto<PhoneUser, PhoneUserResponseDto> mapClassWithResponseDto;
    
    @Override
    public PhoneUserResponseDto findByPhoneNumber(String phoneNumber) throws APIErrorException {
        PhoneUser phoneUser = phoneRepository.findByPhoneNumber(phoneNumber);
        if (phoneUser != null) {
            return mapClassWithResponseDto.convertToDto(phoneUser, PhoneUserResponseDto.class);
        } else {
            throw new APIErrorException(ErrorCode.A515);
        }
    }

    @Override
    public PhoneUserResponseDto findByUuid(String uuid) throws APIErrorException {
        PhoneUser phoneUser = phoneRepository.findByUuid(uuid);
        if (phoneUser == null) {
            throw new APIErrorException(ErrorCode.A515);
        } else {
            return mapClassWithResponseDto.convertToDto(phoneUser, PhoneUserResponseDto.class);
        }
    }

    
    @Override
    public PhoneUser checkTelephoneUuid(String uuid) throws APIErrorException {
        if (uuid == null) {
            throw new APIErrorException(ErrorCode.A0024);
        }
        PhoneUser oTelephone = phoneRepository.findByUuid(uuid);
        if (Objects.isNull(oTelephone)) {
            throw new APIErrorException(ErrorCode.A0028);
        }
        return oTelephone;                   
    }
    @Override
    public Map<String, Object> findAll(int page, int size) throws APIErrorException {
        Pageable pageable = PageRequest.of(page, size);
        Page<PhoneUser> phoneUsersPage = phoneRepository.findAll(pageable);

        if (phoneUsersPage.hasContent()) {
            List<PhoneUserResponseDto> phoneUsers = mapClassWithResponseDto.convertListToListDto(phoneUsersPage.getContent(), PhoneUserResponseDto.class);

            Map<String, Object> response = new HashMap<>();
            response.put("phoneUsers", phoneUsers);
            response.put("currentPage", phoneUsersPage.getNumber());
            response.put("totalItems", phoneUsersPage.getTotalElements());
            response.put("totalPages", phoneUsersPage.getTotalPages());

            return response;
        } else {
            throw new APIErrorException(ErrorCode.A511);
        }
    }

    @Override
    public PhoneUserResponseDto savePhoneUser(PhoneUserRequestDto phoneUserRequestDto) throws APIErrorException {
        if (phoneUserRequestDto == null) {
            throw new APIErrorException(ErrorCode.A507);
        } else {
            if ( phoneRepository.findByPhoneNumber(phoneUserRequestDto.getNumber())!=null) {
                throw new APIErrorException(ErrorCode.A503);
            }
            PhoneUser phoneUser = mapClassWithRequestDto.convertToEntity(phoneUserRequestDto, PhoneUser.class);
            PhoneUser savedPhoneUser = phoneRepository.save(phoneUser);
            return mapClassWithResponseDto.convertToDto(savedPhoneUser, PhoneUserResponseDto.class);
        }
    }

    @Override
    public PhoneUserResponseDto updatePhoneUser(String uuid,PhoneUserUpdateDto requestDto) throws APIErrorException {

        PhoneUser phoneUser = phoneRepository.findByUuid(uuid);
        if (phoneUser != null) {
            if (requestDto.getNumber()==null) {
                phoneUser.setNumbre(requestDto.getNumber());
            }
            PhoneUser updatedPhoneUser = phoneRepository.save(phoneUser);
            return mapClassWithResponseDto.convertToDto(updatedPhoneUser, PhoneUserResponseDto.class);
        } else {
            throw new APIErrorException(ErrorCode.A515);
        }
    }
    
    @Override
    public List<PhoneUserResponseDto> getAllByUser(String uuid) throws APIErrorException {
        List<PhoneUser> phoneUsers = phoneRepository.findAllByUser(uuid);
        if (!phoneUsers.isEmpty()) {
            return mapClassWithResponseDto.convertListToListDto(phoneUsers,PhoneUserResponseDto.class);
        }
        else throw new APIErrorException(ErrorCode.A0022);

    }

    @Override
    public void deletePhoneUser(String uuid) throws APIErrorException {
        PhoneUser phoneUser = phoneRepository.findByUuid(uuid);
        if (phoneUser != null) {
            phoneUser.setDelete(true);
            phoneRepository.delete(phoneUser);
        } else {
            throw new APIErrorException(ErrorCode.A515);
        }
    }
}

