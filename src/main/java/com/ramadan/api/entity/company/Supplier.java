package com.ramadan.api.entity.company;

import org.hibernate.annotations.Where;


import com.ramadan.api.entity.global.BaseModel;
import com.ramadan.api.helpers.Utils;

import jakarta.annotation.PreDestroy;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "ns_supplier")
@Where(clause = "is_delete = false and is_statut = true")
public class Supplier extends BaseModel {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

	@Column(name = "code_type_fournisseur")
	private String codeTypeFournisseur;

	@Column(name = "barcode")
	private String barcode;

	@Column(name = "telephone")
	private String telephone;

	@Column(name = "email")
	private String email;

	@Column(name = "raison_sociale1")
	private String raisonSociale1;

	@Column(name = "raison_sociale2")
	private String raisonSociale2;

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
