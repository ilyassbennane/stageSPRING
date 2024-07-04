package com.ramadan.api.entity.costumer;

import org.hibernate.annotations.Where;

import com.ramadan.api.entity.global.BaseModel;
import com.ramadan.api.helpers.Utils;

import jakarta.annotation.PreDestroy;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
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
@Table(name = "ns_travel_custumer")
@Where(clause = "is_delete = false and is_statut = true")
public class TravelCustumer extends BaseModel {

	private static final long serialVersionUID = 1L;

	@OneToOne
	@JoinColumn(name = "costumer_id")
	private Costumer costumer;

	@Column(name = "encaisement_assure")
	private String encaisementAssure;

	@Column(name = "encaisement_courant")
	private String encaisementCourant;

	@Column(name = "plafond_cheque")
	private String plafondCheque;

	@Column(name = "plafond_bl_cachete")
	private String plafondBLCachete;

	@Column(name = "delai_reglement")
	private Integer delaiReglement;

	@Column(name = "chiffre_affaire")
	private Double chiffreAffaire;

	@Column(name = "recouvrement")
	private Double recouvrement;

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