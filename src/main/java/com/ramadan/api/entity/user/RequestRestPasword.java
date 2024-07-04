package com.ramadan.api.entity.user;

import org.hibernate.annotations.Where;

import com.ramadan.api.entity.global.BaseModel;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@NoArgsConstructor
@Table(name = "ns_request_rest_pasword")
@Where(clause = "is_delete = false and is_statut = true")
public class RequestRestPasword extends BaseModel {
	
	private static final long serialVersionUID = 1L;


}
