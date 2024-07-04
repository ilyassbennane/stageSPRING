package com.ramadan.api.entity.agence;


import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ramadan.api.entity.address.LocationGPS;
import com.ramadan.api.entity.agence.Agency;
import com.ramadan.api.entity.company.Company;
import com.ramadan.api.entity.company.Supplier;
import com.ramadan.api.entity.costumer.Costumer;
import com.ramadan.api.entity.global.BaseModel;
import com.ramadan.api.entity.stock.deposit.Deposit;
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
@Table(name = "ns_address_agency")
@Where(clause = "is_delete = false and is_statut = true")
public class AdresseAgency extends BaseModel {

	private static final long serialVersionUID = 1L;
	
	@OneToOne(cascade = CascadeType.ALL)
    private LocationGPSAdresseAgency locationGPS;
	
	@ManyToOne
	@JoinColumn(name = "agency_id")
	@JsonIgnore
	private Agency agency;

	@Column(name = "is_main", nullable = true, columnDefinition = "boolean default true")
	private boolean isMain;

	@Column(name = "code_ville")
	private String codeVille;

	@Column(name = "address_1")
	private String address1;

	@Column(name = "address_2")
	private String address2;

	@Column(name = "address_3")
	private String address3;
	
    @Column(name="quartier")
    private String quartier;
    
    @Column(name="city")
    private String city;

    @Column(name="country")
    private String country;

    @Column(name="zip_code")
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
