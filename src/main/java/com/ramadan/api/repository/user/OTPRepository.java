package com.ramadan.api.repository.user;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ramadan.api.entity.user.OTP;

@Repository
public interface OTPRepository extends JpaRepository<OTP, Long> {
    @Query("SELECT p FROM OTP p WHERE p.uuid = :uuid")
    OTP findByUuid(@Param("uuid") String uuid);

    @Query("SELECT p FROM OTP p WHERE p.telephone.uuid = :telephoneUuid")
    Page<OTP> findByTelephone(@Param("telephoneUuid") String telephoneUuid, Pageable pageable);
    
    @Query("SELECT p FROM OTP p WHERE p.telephone.uuid = :telephoneUuid and p.isValid = true")
    OTP findByTelephoneAndValid(@Param("telephoneUuid") String telephoneUuid);
    
    @Query("SELECT p FROM OTP p WHERE p.telephone.uuid = :telephoneUuid and p.expiryTime > :expiryTime")
    OTP findByTelephoneAndExpiryTimeAfter(@Param("telephoneUuid") String telephoneUuid, @Param("expiryTime") LocalDateTime expiryTime);
    
    @Query("SELECT p FROM OTP p WHERE p.email.uuid = :emailUuid")
    Page<OTP> findByEmail(@Param("emailUuid") String emailUuid, Pageable pageable);
    
    @Query("SELECT p FROM OTP p WHERE p.email.uuid = :emailUuid and p.isValid = true")
    OTP findByEmailAndValid(@Param("emailUuid") String emailUuid);
    
    @Query("SELECT p FROM OTP p WHERE p.email.uuid = :emailUuid and p.expiryTime > :expiryTime")
    OTP findByEmailAndExpiryTimeAfter(@Param("emailUuid") String emailUuid, @Param("expiryTime") LocalDateTime expiryTime);

//    
}
