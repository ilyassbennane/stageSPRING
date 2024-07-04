package com.ramadan.api.entity.document;

import java.util.Date;

import org.hibernate.annotations.Where;

import com.ramadan.api.entity.global.BaseModel;
import com.ramadan.api.entity.user.User;
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
@Table(name = "ns_deadline")
@Where(clause = "is_delete = false and is_statut = true")
public class Deadline extends BaseModel {

	@OneToOne(cascade = CascadeType.ALL)
	private LocationGPSDeadline locationGPSDeadline;

	@ManyToOne
	@JoinColumn(name = "user_creation_id")
	private User userCreation;

	@ManyToOne
	@JoinColumn(name = "document_id")
	private Document document;

	@Column(name = "code_etat_deadline")
	private String codeEtatDeadline;

	@Column(name = "code_statut_deadline")
	private String codeStatutDeadline;

	private static final long serialVersionUID = 1L;

	@Column(name = "code_dealine_mobile")
	private String codeDeadlineMobile;

	@Column(name = "code_dealine_system")
	private String codeDeadlineSystem;

	@Column(name = "code_dealine")
	private String codeDeadline;

	@Column(name = "date_deadline")
	private Date dateDeadline;

	@Column(name = "date_deadline_last")
	private Date dateDeadlineLast;

	@Column(name = "montant_facture")
	private Double montantFacture;

	@Column(name = "montant_a_paye")
	private Double montantAPaye;

	@Column(name = "montant_rest_a_paye")
	private Double montantRestAPaye;

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
