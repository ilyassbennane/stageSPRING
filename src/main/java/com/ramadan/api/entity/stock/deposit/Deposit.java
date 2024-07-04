package com.ramadan.api.entity.stock.deposit;

import org.hibernate.annotations.Where;

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
@Table(name = "ns_deposit_mobile")
@Where(clause = "is_delete = false and is_statut = true")
public class Deposit extends BaseModel {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

	@Column(name = "is_main", nullable = true, columnDefinition = "boolean default true")
	private boolean isMain;

	@Column(name = "matricule")
	private String matricule;

	@Column(name = "volume")
	private Double volume;

	@Column(name = "poids")
	private Double poids;

	@Column(name = "code_type_deposit")
	private String codeTypeDeposit;

	@Column(name = "code_unite_poids")
	private String codeUnitePoids;

	@Column(name = "code_unite_volume")
	private String codeUniteVolume;

	@Column(name = "temperature_minimale")
	private Double temperature_minimale;

	@Column(name = "temperature_maximale")
	private Double temperature_maximale;

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
