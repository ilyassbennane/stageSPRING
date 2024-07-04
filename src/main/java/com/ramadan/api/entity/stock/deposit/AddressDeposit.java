package com.ramadan.api.entity.stock.deposit;

import org.hibernate.annotations.Where;

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
@Table(name = "ns_address_deposit")
@Where(clause = "is_delete = false and is_statut = true")
public class AddressDeposit extends BaseModel {

	private static final long serialVersionUID = 1L;

	@OneToOne(cascade = CascadeType.ALL)
	private LocationGPSAddressDeposit locationGPS;

	@ManyToOne
	@JoinColumn(name = "deposit_id")
	private Deposit deposit;

	@Column(name = "is_main", nullable = true, columnDefinition = "boolean default true")
	private boolean isMain;

	@Column(name = "code_ville")
	private String codeVille;

	@Column(name = "adresse_1")
	private String adresse1;

	@Column(name = "adresse_2")
	private String adresse2;

	@Column(name = "adresse_3")
	private String adresse3;

	@Column(name = "quartier")
	private String quartier;

	@Column(name = "city")
	private String city;

	@Column(name = "country")
	private String country;

	@Column(name = "zip_code")
	private String zipCode;

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
