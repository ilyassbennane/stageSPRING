package com.ramadan.api.entity.user;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.Where;

import com.ramadan.api.entity.device.Device;
import com.ramadan.api.entity.global.BaseModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@NoArgsConstructor
@Table(name = "ns_historique_login")
@Where(clause = "is_delete = false and is_statut = true")
public class HistoriqueLogin extends BaseModel {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "device_id")
	private Device device;

	@ManyToOne
	@JoinColumn(name = "id_use")
	private User user;

	@Column(name = "date_historique", nullable = true)
	private LocalDateTime dateHistorique;

	@Column(name = "gps_latitude", nullable = true)
	private BigDecimal gpsLatitude;

	@Column(name = "gps_longitude", nullable = true)
	private BigDecimal gpsLongitude;
}
