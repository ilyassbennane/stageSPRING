package com.ramadan.api.services.user.emailUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ramadan.api.dto.MapClassWithDto;
import com.ramadan.api.dto.user.email.EmailRequestDto;
import com.ramadan.api.dto.user.email.EmailResponseDto;
import com.ramadan.api.dto.user.email.EmailUpdateDto;
import com.ramadan.api.entity.user.EmailUser;
import com.ramadan.api.entity.user.User;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ErrorCode;
import com.ramadan.api.repository.user.EmailRepository;

@Service
@Transactional

public class EmailUserService implements IEmailUserService{
	 @Autowired
	    private EmailRepository emailRepository;

	    @Autowired
	    private MapClassWithDto<EmailUser, EmailRequestDto> mapClassWithRequestDto;

	    @Autowired
	    private MapClassWithDto<EmailUser, EmailResponseDto> mapClassWithResponseDto;

	    
    @Override
    public EmailResponseDto findByUuid(String uuid) throws APIErrorException {
        EmailUser emailUser = emailRepository.findByUuid(uuid);
        if (emailUser != null) {
            return mapClassWithResponseDto.convertToDto(emailUser,EmailResponseDto.class);
        } else {
            throw new APIErrorException(ErrorCode.A509);
        }
    }



    @Override
    public List<EmailResponseDto> getAllByUser(String uuid) throws APIErrorException {
        List<EmailUser> emailUsers = emailRepository.findAllByUser(uuid);
        if (!emailUsers.isEmpty()) {
            return mapClassWithResponseDto.convertListToListDto(emailUsers,EmailResponseDto.class);
        }
        else throw new APIErrorException(ErrorCode.A511);

    }
    
    @Override
    public EmailUser checkEmailUuid(String uuid) throws APIErrorException {
        if (uuid == null) {
            throw new APIErrorException(ErrorCode.A0014);
        }
        EmailUser oEmail = emailRepository.findByUuid(uuid);
        if (Objects.isNull(oEmail)) {
            throw new APIErrorException(ErrorCode.A0011);
        }
        
        return oEmail;                   
    }
    
    @Override
    public Map<String, Object> findAll(int page, int size) throws APIErrorException {
        Pageable pageable = PageRequest.of(page, size);
        Page<EmailUser> emailUsersPage = emailRepository.findAll(pageable);

        if (emailUsersPage.hasContent()) {
            List<EmailResponseDto> emails = mapClassWithResponseDto.convertListToListDto(emailUsersPage.getContent(), EmailResponseDto.class);

            Map<String, Object> response = new HashMap<>();
            response.put("emails", emails);
            response.put("currentPage", emailUsersPage.getNumber());
            response.put("totalItems", emailUsersPage.getTotalElements());
            response.put("totalPages", emailUsersPage.getTotalPages());

            return response;
        } else {
            throw new APIErrorException(ErrorCode.A511);
        }
    }


    @Override
    public EmailResponseDto updateEmailUser(String uuid, EmailUpdateDto requestDto) throws APIErrorException {
        EmailUser existingEmail = emailRepository.findByUuid(uuid);
        if (existingEmail  == null) {
            throw new APIErrorException(ErrorCode.A509);
        } else {

            if (requestDto.getEmail()!=null ) {
                existingEmail.setEmail(requestDto.getEmail());
            }
            EmailUser updatedEmailUser = emailRepository.save(existingEmail);
            return mapClassWithResponseDto.convertToDto( updatedEmailUser, EmailResponseDto.class);
        }
    }

    @Override
    public void deleteEmailUser(String uuid) throws APIErrorException {
        EmailUser emailUser = emailRepository.findByUuid(uuid);
        if (emailUser != null) {
            emailUser.setDelete(true);
            emailRepository.save(emailUser);
        } else {
            throw new APIErrorException(ErrorCode.A509);
        }
    }

    @Override
    public EmailResponseDto saveEmailUser(EmailRequestDto emailRequestDto) throws APIErrorException {
        if (emailRequestDto == null) {
            throw new APIErrorException(ErrorCode.A507);
        } else {
            if (emailRepository.findByEmail(emailRequestDto.getEmail())!=null) {
                throw new APIErrorException(ErrorCode.A502);
            }
            EmailUser emailUser = mapClassWithRequestDto.convertToEntity(emailRequestDto, EmailUser.class);
            EmailUser savedEmailUser = emailRepository.save(emailUser);
            return mapClassWithResponseDto.convertToDto(savedEmailUser, EmailResponseDto.class);
        }
    }

    @Override
    public EmailUser findByUserAndIsMain(User user) throws APIErrorException {
        EmailUser emailUser = emailRepository.findByUserAndIsMain(user);
        if (emailUser == null) {
            throw new APIErrorException(ErrorCode.A509);
        }
        return emailUser;
    }


}
