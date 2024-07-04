package com.ramadan.api.entity.device;

import org.hibernate.annotations.Where;

import com.ramadan.api.entity.company.Company;
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
@Table(name = "ns_device")
@Where(clause = "is_delete = false and is_statut = true")
public class Device extends BaseModel {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

	@Column(name = "code_type_device")
	private String codeTypeDevice;

	@Column(name = "code_marque")
	private String codeMarque;

	@Column(name = "code_module")
	private String codeModule;

	@Column(name = "cordova")
	private String cordova;

	@Column(name = "code_plateform")
	private String codePlateform;

	@Column(name = "version")
	private String version;

	@Column(name = "emei")
	private String emei;

	@Column(name = "rang")
	private Integer rang;

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
