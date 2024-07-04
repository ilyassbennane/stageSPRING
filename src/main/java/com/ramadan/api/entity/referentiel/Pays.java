package com.ramadan.api.entity.referentiel;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import com.ramadan.api.helpers.Utils;

import jakarta.annotation.PreDestroy;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "ref_pays")
@Where(clause = "is_delete = false and is_statut = true")
public class Pays implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Pays(String code, String valeur) {
		super();
		this.code = code;
		this.valeur = valeur;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	@Setter(AccessLevel.NONE)
	protected Long id;

	@Column
	protected String code;

	@Column
	protected String uuid;

	@Column
	protected String description;

	@Column
	protected String valeur;

	@Column(columnDefinition = "boolean default false")
	protected boolean isDelete;

	@Column(columnDefinition = "boolean default true")
	protected boolean isStatut;

	@CreationTimestamp
	protected LocalDateTime createDateTime;

	@UpdateTimestamp
	protected LocalDateTime updateDateTime;

	@PrePersist
	public void prePersist() {
		this.isDelete = false;
		this.isStatut = true;
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

	@PostPersist
	public void postPersist() {
		String uid = Utils.getHashedUuid(this.createDateTime, this.getId());
		this.setUuid(uid);

	}

}
