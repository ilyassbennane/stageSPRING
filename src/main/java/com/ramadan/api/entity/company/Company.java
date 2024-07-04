package com.ramadan.api.entity.company;

import org.hibernate.annotations.Where;

import com.ramadan.api.entity.global.BaseModel;
import com.ramadan.api.helpers.Utils;

import jakarta.annotation.PreDestroy;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PostPersist;
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
@Table(name = "ns_company")
@Where(clause = "is_delete = false and is_statut = true")
public class Company extends BaseModel {

	private static final long serialVersionUID = 1L;

	@Column(name = "prefix")
	private String prefix;

	@Column(name = "telephone")
	private String telephone;

	@Column(name = "fax")
	private String fax;

	@Column(name = "pattente")
	private String pattente;

	@Column(name = "capital")
	private String capital;

	@Column(name = "registre_commercial")
	private String registreCommercial;

	@Column(name = "identifiant_fiscal")
	private String identifiantFiscal;

	@Column(name = "institution_civil_engineers")
	private String institutionCivilEngineers;

	@Column(name = "travaux_public")
	private String travauxPublic;

	@Column(name = "code_ville")
	private String codeVille;

	@Column(name = "cnss")
	private String cnss;

	@Column(name = "web")
	private String web;

	@Column(name = "code_forme_juridique")
	private String codeFormeJuridique;

	@Column(name = "code_secteur_activite")
	private String codeSecteurActivite;

	@PostPersist
	public void postPersist() {
		String Uuid = Utils.getHashedUuid(getCreateDateTime(), getId());
		this.setUuid(Uuid);
		String code = Utils.generateHash();
		this.setCode(code);
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
