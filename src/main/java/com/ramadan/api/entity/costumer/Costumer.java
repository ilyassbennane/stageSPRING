package com.ramadan.api.entity.costumer;

import org.hibernate.annotations.Where;

import com.ramadan.api.entity.agence.Sector;
import com.ramadan.api.entity.global.BaseModel;
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
@Table(name = "ns_costumer")
@Where(clause = "is_delete = false and is_statut = true")
public class Costumer extends BaseModel {

	@OneToOne(cascade = CascadeType.ALL)
	private InfosBusiness infosBusiness;

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "family_costumer_id")
	private FamilyCostumer familyCostumer;

	@Column(name = "code_costumer_mobile")
	private String codeCostumerMobile;

	@Column(name = "code_costumer_system")
	private String codeCostumerSystem;

	@Column(name = "code_costumer")
	private String codeCostumer;

	@ManyToOne
	@JoinColumn(name = "sector_id")
	private Sector sector;

	@Column(name = "is_favorite")
	private boolean isFavorite;

	@Column(name = "code_tva")
	private String codeTva;

	@Column(name = "code_type_costumer")
	private String codeTypeCostumer;

	@Column(name = "code_gender")
	private String codeGender;

	@Column(name = "raison_sociale1")
	private String raisonSociale1;

	@Column(name = "raison_sociale2")
	private String raisonSociale2;

	@Column(name = "barcode")
	private String barcode;

	@Column(name = "rang")
	private Integer rang;

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
