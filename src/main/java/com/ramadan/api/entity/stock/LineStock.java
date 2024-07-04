package com.ramadan.api.entity.stock;

import java.util.Date;

import org.hibernate.annotations.Where;

import com.ramadan.api.entity.global.BaseModel;
import com.ramadan.api.entity.product.Product;
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
@Table(name = "ns_line_stock")
@Where(clause = "is_delete = false and is_statut = true")
public class LineStock extends BaseModel {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "stock_id")
	private Stock stock;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@Column(name = "rang")
	private Integer rang;

	@Column(name = "code_etat_stock")
	private String codeEtatStock;

	@Column(name = "code_unit_weight")
	private String codeUnitWeight;

	@Column(name = "code_unite_volume")
	private String codeUniteVolume;

	@Column(name = "code_unit_quantity_damage")
	private String codeUnitQuantityDamage;

	@Column(name = "code_unite_quantity_undamaged")
	private String codeUniteQuantityUndamaged;

	@Column(name = "weight")
	private Double weight;

	@Column(name = "volume")
	private Double volume;

	@Column(name = "value")
	private Double value;

	@Column(name = "quantity_undamaged")
	private Double quantityUndamaged;

	@Column(name = "quantity_damage")
	private Double quantityDamage;

	@Column(name = "date_create")
	private Date dateCreate;

	@Column(name = "date_update")
	private Date dateUpdate;

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
