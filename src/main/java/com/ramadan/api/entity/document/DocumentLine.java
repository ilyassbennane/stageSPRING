package com.ramadan.api.entity.document;

import java.util.Date;

import org.hibernate.annotations.Where;

import com.ramadan.api.entity.global.BaseModel;
import com.ramadan.api.helpers.Utils;

import jakarta.annotation.PreDestroy;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "ns_document_line")
@Where(clause = "is_delete = false and is_statut = true")
public class DocumentLine extends BaseModel {

	private static final long serialVersionUID = 1L;

	@Column(name = "code_type_line")
	private String codeTypeLine;

	@Column(name = "code_etat_product")
	private String codeEtatProduct;

	@Column(name = "code_tva")
	private String codeTva;

	@Column(name = "code_unit_weight")
	private String codeUnitWeight;

	@Column(name = "code_unite_volume")
	private String codeUniteVolume;

	@Column(name = "code_unit_quantity")
	private String codeUnitQuantity;

	@Column(name = "quantite")
	private Double quantite;

	@Column(name = "volume")
	private Double volume;

	@Column(name = "poids")
	private Double poids;

	@Column(name = "montant_ttc")
	private Double montantTTC;

	@Column(name = "montant_ht")
	private Double montantHT;

	@Column(name = "montant_total_offre_ttc")
	private Double montantTotalOffreTTC;

	@Column(name = "montant_total_offre_ht")
	private Double montantTotalOffreHT;

	@Column(name = "montant_tva")
	private Double montantTva;

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
