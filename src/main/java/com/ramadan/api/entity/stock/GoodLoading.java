package com.ramadan.api.entity.stock;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.annotations.Where;

import com.ramadan.api.entity.global.BaseModel;
import com.ramadan.api.entity.stock.deposit.Deposit;
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
@Table(name = "ns_good_loading")
@Where(clause = "is_delete = false and is_statut = true")
public class GoodLoading extends BaseModel {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "deposit_unloading_id")
	private Deposit depositUnloading;

	@ManyToOne
	@JoinColumn(name = "deposit_loading_id")
	private Deposit depositLoading;

	@ManyToOne
	@JoinColumn(name = "user_demande_id")
	private User userDemande;

	@ManyToOne
	@JoinColumn(name = "user_processing_id")
	private User userProcessing;

	@ManyToOne
	@JoinColumn(name = "user_load_id")
	private User userLoad;

	@Column(name = "code_etat_good_loading")
	private String codeEtatGoodLoading;

	@Column(name = "code_statut_good_loading")
	private String codeStatutGoodLoading;

	@Column(name = "code_good_loading_mobile")
	private String codeGoodLoadingMobile;

	@Column(name = "code_good_loading_system")
	private String codeGoodLoadingSystem;

	@Column(name = "code_good_loading")
	private String codeGoodLoading;

	@Column(name = "code_unit_weight")
	private String codeUnitWeight;

	@Column(name = "code_unite_volume")
	private String codeUniteVolume;

	@Column(name = "number_product")
	private Integer numberProduct;

	@Column(name = "weight")
	private Double weight;

	@Column(name = "volume")
	private Double volume;

	@Column(name = "value")
	private Double value;

	@Column(name = "gps_latitude_demande", nullable = true)
	private BigDecimal gpsLatitudeDemande;

	@Column(name = "gps_longitude_demande", nullable = true)
	private BigDecimal gpsLongitudeDemande;

	@Column(name = "gps_latitude_load", nullable = true)
	private BigDecimal gpsLatitudeLoad;

	@Column(name = "gps_longitude_load", nullable = true)
	private BigDecimal gpsLongitudeLoad;

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
