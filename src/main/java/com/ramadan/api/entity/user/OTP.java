package com.ramadan.api.entity.user;

import java.time.LocalDateTime;

import org.hibernate.annotations.Where;

import com.ramadan.api.entity.global.BaseModel;
import com.ramadan.api.helpers.Utils;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "nitro_otp")
@Where(clause = "is_delete = false and is_statut = true")
public class OTP extends BaseModel {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
    @JoinColumn(name=" telephone_id")
    private PhoneUser telephone ;
	
	@ManyToOne
    @JoinColumn(name=" email_id")
    private EmailUser email;
	
	@Column(name= "expiry_Time")
	private LocalDateTime expiryTime;
	  
    @Column(name= "otp")
    private String otp;
    
    @Override
    @PostPersist
    public void postPersist() {
        String Uuid = Utils.getHashedUuid(this.getCreateDateTime(), this.getId());
        this.setUuid(Uuid);
        String otp = Utils.generateOtp();
        this.setOtp(otp);
    }

}

