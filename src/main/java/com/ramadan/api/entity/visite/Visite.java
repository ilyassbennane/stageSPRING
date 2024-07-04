package com.ramadan.api.entity.visite;

import java.util.Date;

import org.hibernate.annotations.Where;

import com.ramadan.api.entity.costumer.Costumer;
import com.ramadan.api.entity.global.BaseModel;
import com.ramadan.api.entity.tour.Tour;
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
@Table(name = "ns_visite")
@Where(clause = "is_delete = false and is_statut = true")
public class Visite extends BaseModel {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "tour_id")
	private Tour tour;

	@ManyToOne
	@JoinColumn(name = "costumer_id")
	private Costumer costumer;

	@Column(name = "date_debut")
	private Date dateDebut;

	@Column(name = "date_fin")
	private Date dateFin;

	@Column(name = "date_update")
	private Date dateUpdate;

	@Column(name = "code_statut_visite")
	private String codeStatutVisite;

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