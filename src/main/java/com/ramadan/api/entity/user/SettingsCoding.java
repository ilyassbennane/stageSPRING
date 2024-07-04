package com.ramadan.api.entity.user;

import org.hibernate.annotations.Where;

import com.ramadan.api.entity.global.BaseModel;
import com.ramadan.api.helpers.Utils;

import jakarta.annotation.PreDestroy;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "ns_settings_coding")
@Where(clause = "is_delete = false and is_statut = true")
public class SettingsCoding extends BaseModel {

	private static final long serialVersionUID = 1L;

	@Column(name = "code_delivery_note", nullable = true, unique = false)
	private String codeDeliveryNote;

	@Column(name = "sequence_delivery_note", nullable = true, unique = false)
	private Integer sequenceDeliveryNote;

	@Column(name = "code_order_taking", nullable = true, unique = false)
	private String codeOrderTaking;

	@Column(name = "sequence_order_taking", nullable = true, unique = false)
	private Integer sequenceOrderTaking;

	@Column(name = "code_invoice", nullable = true, unique = false)
	private String codeInvoice;

	@Column(name = "sequence_invoice", nullable = true, unique = false)
	private Integer sequenceInvoice;

	@Column(name = "code_mouvment", nullable = true, unique = false)
	private String codeMouvment;

	@Column(name = "sequence_mouvment", nullable = true, unique = false)
	private Integer sequenceMouvment;

	@Column(name = "code_good_loading", nullable = true, unique = false)
	private String codeGoodLoading;

	@Column(name = "sequence_good_loading", nullable = true, unique = false)
	private Integer sequenceGoodLoading;

	@Column(name = "code_good_unloading", nullable = true, unique = false)
	private String codeGoodUnloading;

	@Column(name = "sequence_good_unloading", nullable = true, unique = false)
	private Integer sequenceGoodUnloading;

	@Column(name = "code_payment", nullable = true, unique = false)
	private String codePayment;

	@Column(name = "sequence_payment", nullable = true, unique = false)
	private Integer sequencePayment;

	@Column(name = "code_deadline", nullable = true, unique = false)
	private String codeDeadline;

	@Column(name = "sequence_deadline", nullable = true, unique = false)
	private Integer sequenceDeadline;

	@Column(name = "code_tohave", nullable = true, unique = false)
	private String codeTohave;

	@Column(name = "sequence_tohave", nullable = true, unique = false)
	private Integer sequenceTohave;

	@Column(name = "code_costumer", nullable = true, unique = false)
	private String codeCostumer;

	@Column(name = "sequence_costumer", nullable = true, unique = false)
	private Integer sequenceCostumer;

	@Column(name = "code_visite", nullable = true, unique = false)
	private String codeVisite;

	@Column(name = "sequence_visite", nullable = true, unique = false)
	private Integer sequenceVisite;

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
