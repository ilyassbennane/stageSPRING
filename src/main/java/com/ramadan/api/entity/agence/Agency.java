package com.ramadan.api.entity.agence;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ramadan.api.entity.company.Company;
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
@Table(name = "ns_agency")
@Where(clause = "is_delete = false and is_statut = true")
public class Agency extends BaseModel {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "company_id")
	@JsonIgnore
	private Company company;


	@Column(name = "is_main", nullable = true, columnDefinition = "boolean default true")
	private boolean isMain;

	@Column(name = "pattente")
	private String pattente;

	@Column(name = "capital")
	private String capital;

	@Column(name = "registreCommercial")
	private String registreCommercial;

	@Column(name = "identifiant_fiscal")
	private String identifiantFiscal;

	@Column(name = "travaux_public")
	private String travauxPublic;

	@Column(name = "cnss")
	private String cnss;

	@Column(name = "web")
	private String web;

	@Column(name = "code_ville")
	private String codeVille;

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
