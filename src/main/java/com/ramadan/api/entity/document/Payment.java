package com.ramadan.api.entity.document;

import java.util.Date;

import org.hibernate.annotations.Where;

import com.ramadan.api.entity.global.BaseModel;
import com.ramadan.api.entity.user.User;
import com.ramadan.api.helpers.Utils;

import jakarta.annotation.PreDestroy;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@NoArgsConstructor
@Table(name = "ns_payment")
@Where(clause = "is_delete = false and is_statut = true")
public class Payment extends BaseModel {

	@OneToOne(cascade = CascadeType.ALL)
	private LocationGPSPayment locationGPSPayment;

	@ManyToOne
	@JoinColumn(name = "user_creation_id")
	private User userCreation;

	@Column(name = "code_statut_payment")
	private String codeStatutPayment;

	@Column(name = "code_payment_mobile")
	private String codePaymentMobile;

	@Column(name = "code_payment_system")
	private String codePaymentSystem;

	private static final long serialVersionUID = 1L;

	@JoinColumn(name = "code_payment_method")
	private String codePaymentMethod;

	@Column(name = "montant_payment")
	private Double montantPayment;

	@Column(name = "date_create")
	private Date dateCreate;

	@Column(name = "date_update")
	private Date dateUpdate;

	@PostPersist
	public void postPersist() {
		String Uuid = Utils.getHashedUuid(getCreateDateTime(), getId());
		this.setUuid(Uuid);
		String code = Utils.generateHash();
		this.setCode(code);
	}

	@PrePersist
	public void prePersist() {
		this.setDelete(false);
		this.setStatut(true);
		this.setActive(true);
		this.setValid(true);
	}

	@PreUpdate
	public void preUpdate() {

	}

	@PreRemove
	public void preRemove() {

	}

	@PreDestroy
	public void preDestroy() {

	}

}
