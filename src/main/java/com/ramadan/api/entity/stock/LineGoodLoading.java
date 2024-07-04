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
@Table(name = "ns_line_good_loading")
@Where(clause = "is_delete = false and is_statut = true")
public class LineGoodLoading extends BaseModel {

	private static final long serialVersionUID = 1L;

	@Column(name = "code_etat_line_good_loading")
	private String codeEtatLineGoodLoading;

	@ManyToOne
	@JoinColumn(name = "good_loading_id")
	private GoodLoading goodLoading;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@Column(name = "rang")
	private Integer rang;

	@Column(name = "code_unit_quantity_demande")
	private String codeUnitQuantityDemande;

	@Column(name = "code_unit_quantity_Valide")
	private String codeUnitQuantityValide;

	@Column(name = "code_unit_quantity_load")
	private String codeUnitQuantityLoad;

	@Column(name = "quantity_demande")
	private Double quantityDemande;

	@Column(name = "quantity_valide")
	private Double quantityValide;

	@Column(name = "value_load")
	private Double valueLoad;

	@Column(name = "value_valide")
	private Double valueValide;

	@Column(name = "value_demande")
	private Double valueDemande;

	@Column(name = "quantity_load")
	private Double quantityLoad;

	@Column(name = "date_create")
	private Date dateCreate;

	@Column(name = "date_update")
	private Date dateUpdate;

	@Column(name = "date_valide")
	private Date dateValide;

	@Column(name = "date_load")
	private Date dateLoad;

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
