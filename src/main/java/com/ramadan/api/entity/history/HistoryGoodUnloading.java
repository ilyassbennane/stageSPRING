package com.ramadan.api.entity.history;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.annotations.Where;

import com.ramadan.api.entity.global.BaseModel;
import com.ramadan.api.entity.stock.GoodUnloading;
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
@Table(name = "ns_history_good_unloading")
@Where(clause = "is_delete = false and is_statut = true")
public class HistoryGoodUnloading extends BaseModel {

	private static final long serialVersionUID = 1L;

	@Column(name = "code_action")
	private String codeAction;

	@ManyToOne
	@JoinColumn(name = "good_unloading_id")
	private GoodUnloading goodUnloading;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "gps_latitude", nullable = true)
	private BigDecimal gpsLatitude;

	@Column(name = "gps_longitude", nullable = true)
	private BigDecimal gpsLongitude;

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
