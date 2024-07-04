package com.ramadan.api.entity.tour;

import java.util.Date;

import org.hibernate.annotations.Where;

import com.ramadan.api.entity.global.BaseModel;
import com.ramadan.api.helpers.Utils;

import jakarta.annotation.PreDestroy;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
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
@Table(name = "ns_setting_tour")
@Where(clause = "is_delete = false and is_statut = true")
public class SettingTour extends BaseModel {

	private static final long serialVersionUID = 1L;

	@OneToOne
	@JoinColumn(name = "tour_id")
	private Tour tour;

	@Column(name = "is_extra", nullable = true, columnDefinition = "boolean default false")
	private boolean isExtra;

	@Column(name = "is_repeat", nullable = true, columnDefinition = "boolean default false")
	private boolean isRepety;

	@Column(name = "is_open_auto", nullable = true, columnDefinition = "boolean default false")
	private boolean isOpenAuto;

	@Column(name = "is_close_auto", nullable = true, columnDefinition = "boolean default false")
	private boolean isCloseAuto;

	@Column(name = "is_periode_acces", nullable = true, columnDefinition = "boolean default false")
	private boolean isPeriodeAcces;

	@Column(name = "code_periode_acces")
	private String codePeriodecAcces;

	@Column(name = "date_debut")
	private Date dateDebut;

	@Column(name = "date_fin")
	private Date dateFin;

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
