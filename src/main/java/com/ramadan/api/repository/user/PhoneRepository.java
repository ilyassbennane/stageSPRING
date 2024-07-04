package com.ramadan.api.repository.user;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ramadan.api.entity.user.PhoneUser;
import com.ramadan.api.entity.user.User;


public interface PhoneRepository extends JpaRepository<PhoneUser, Long> {
    @Query("select a from PhoneUser a where a.numbre=:Uuid")
    PhoneUser findByPhoneNumber(@Param("Uuid")String phoneNumber);

    @Query("select a from PhoneUser a where a.uuid=:Uuid")
    PhoneUser findByUuid(@Param("Uuid")String uuid);
    
    @Query("select a from PhoneUser a where a.user.uuid=:Uuid")
    List<PhoneUser> findAllByUser(@Param("Uuid")String uuid);
    
    @Query("SELECT p FROM PhoneUser p WHERE p.isMain = true AND p.user = :user")
    PhoneUser findPrincipal(@Param("user") User user);
    
    @Query("SELECT p FROM PhoneUser p WHERE p.numbre LIKE %:telephone%")
    PhoneUser findByNumber(@Param("telephone") String telephone);
    
    @Query("SELECT p FROM PhoneUser p WHERE p.user.uuid = :userUuid")
    PhoneUser findByUserUuid(@Param("userUuid") String userUuid);
}