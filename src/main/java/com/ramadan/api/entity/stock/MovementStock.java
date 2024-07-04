package com.ramadan.api.entity.stock;

import java.util.Date;

import org.hibernate.annotations.Where;

import com.ramadan.api.entity.document.DocumentLine;
import com.ramadan.api.entity.global.BaseModel;
import com.ramadan.api.entity.stock.deposit.Deposit;
import com.ramadan.api.entity.visite.Visite;
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
@Table(name = "ns_movement_stock")
@Where(clause = "is_delete = false and is_statut = true")
public class MovementStock extends BaseModel {

	@OneToOne(cascade = CascadeType.ALL)
	private LocationGPSMovementStock locationGPS;

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "visite_id")
	private Visite visite;

	@ManyToOne
	@JoinColumn(name = "line_stock_id")
	private LineStock lineStock;

	@ManyToOne
	@JoinColumn(name = "document_line_id")
	private DocumentLine documentLine;

	@ManyToOne
	@JoinColumn(name = "ling_good_loading_id")
	private LineGoodLoading lingGoodLoading;

	@ManyToOne
	@JoinColumn(name = "ling_good_unloading_id")
	private LineGoodUnloading lingGoodUnloading;

	@ManyToOne
	@JoinColumn(name = "deposit_initial_id")
	private Deposit depositInitial;

	@ManyToOne
	@JoinColumn(name = "deposit_final_id")
	private Deposit depositFinal;

	@Column(name = "code_statut_movement")
	private String uuidStatutMovement;

	@Column(name = "code_type_movement")
	private String uuidTypeMovement;

	@Column(name = "code_etat_product")
	private String codeEtatProduct;

	@Column(name = "code_unit_quantity_initial")
	private String codeUnitQuantityInitial;

	@Column(name = "code_unit_quantity_final")
	private String codeUnitQuantityFinal;

	@Column(name = "code_unit_quantity_movement")
	private String codeUnitQuantityMovement;

	@Column(name = "quantity_initial")
	private Double quantityInitial;

	@Column(name = "quantity_final")
	private Double quantityFinal;

	@Column(name = "quantity_movement")
	private Double quantityMovement;

	@Column(name = "date_create")
	private Date dateCreate;

	@Column(name = "date_valide")
	private Date dateValide;

	@Column(name = "date_movement")
	private Date dateMovement;

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
