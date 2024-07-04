package com.ramadan.api.entity.product;

import org.hibernate.annotations.Where;

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
@Table(name = "ns_product")
@Where(clause = "is_delete = false and is_statut = true")
public class Product extends BaseModel {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;

	
	@ManyToOne
	@JoinColumn(name = "product_category_id")
	private ProductCategory productCategory;

	@Column(name = "code_tva")
	private String codeTva;

	@Column(name = "name")
	private String name;

	@Column(name = "codebare")
	private String codebare;

	@Column(name = "prix_vente")
	private Double prixVente;

	@Column(name = "prix_achat")
	private Double prixAchat;

	@Column(name = "prix_retour")
	private Double prixRetour;

	@Column(name = "code_unite")
	private String codeUnit;

	@Column(name = "rang")
	private Integer rang;

	@Column(name = "nombre_jours_expiration")
	private Integer nombreJoursExpiration;

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