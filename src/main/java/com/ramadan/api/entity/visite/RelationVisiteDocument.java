package com.ramadan.api.entity.visite;

import org.hibernate.annotations.Where;

import com.ramadan.api.entity.document.Document;
import com.ramadan.api.entity.global.BaseModel;
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
@Table(name = "ns_relation_visite_document")
@Where(clause = "is_delete = false and is_statut = true")
public class RelationVisiteDocument extends BaseModel {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "visite_id")
	private Visite viste;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "document_id")
	private Document document;

	@Column(name = "code_action_visite")
	private String codeActionVisite;

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
