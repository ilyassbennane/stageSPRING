package com.ramadan.api.entity.document;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Where;

import com.ramadan.api.entity.costumer.Costumer;
import com.ramadan.api.entity.global.BaseModel;
import com.ramadan.api.entity.user.User;
import com.ramadan.api.helpers.Utils;

import jakarta.annotation.PreDestroy;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "ns_document")
@Where(clause = "is_delete = false and is_statut = true")
public class Document extends BaseModel {

	@OneToOne(cascade = CascadeType.ALL)
	private LocationGPSDocument locationGPSDocument;

	@OneToOne
	@JoinColumn(name = "parent_id")
	private Document parent;

	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
	private Set<Document> children = new HashSet<>();

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "user_creation_id")
	private User userCreation;

	@ManyToOne
	@JoinColumn(name = "costumer_id")
	private Costumer costumer;

	@Column(name = "code_statut_document")
	private String codeStatutDocument;

	@Column(name = "code_unite_poids")
	private String codeUnitePoids;

	@Column(name = "code_unite_volume")
	private String codeUniteVolume;

	@Column(name = "code_type_document")
	private String codeTypeDocument;

	@Column(name = "code_document")
	private String codeDocument;

	@Column(name = "montant_total")
	private Double montantTotal;

	@Column(name = "poids")
	private Double poids;

	@Column(name = "volume")
	private Double volume;

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
