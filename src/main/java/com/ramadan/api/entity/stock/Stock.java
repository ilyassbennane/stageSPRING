package com.ramadan.api.entity.stock;

import org.hibernate.annotations.Where;

import com.ramadan.api.entity.agence.Agency;
import com.ramadan.api.entity.global.BaseModel;
import com.ramadan.api.entity.stock.deposit.Deposit;
import com.ramadan.api.entity.user.User;
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
@Table(name = "ns_stock")
@Where(clause = "is_delete = false and is_statut = true")
public class Stock extends BaseModel {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "agency_id")
	private Agency agency;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "deposit_id")
	private Deposit deposit;

	@Column(name = "rang")
	private Integer rang;

	@Column(name = "code_unit_weight")
	private String codeUnitWeight;

	@Column(name = "code_unite_volume")
	private String codeUniteVolume;

	@Column(name = "number_product")
	private Integer numberProduct;

	@Column(name = "weight")
	private Double weight;

	@Column(name = "volume")
	private Double volume;

	@Column(name = "value")
	private Double value;

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