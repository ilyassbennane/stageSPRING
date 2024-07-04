package com.ramadan.api.entity.user;

import org.hibernate.annotations.Where;

import com.ramadan.api.entity.company.Company;
import com.ramadan.api.entity.global.BaseModel;
import com.ramadan.api.entity.user.profile.Profile;
import com.ramadan.api.helpers.Utils;

import jakarta.annotation.PreDestroy;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PostPersist;
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
@Table(name = "ns_user")
@Where(clause = "is_delete = false and is_statut = true")
public class User extends BaseModel {

	private static final long serialVersionUID = 1L;

	@OneToOne(cascade = CascadeType.ALL)
	private SettingsCoding settingsCoding;

	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

	@ManyToOne
	@JoinColumn(name = "profile_id")
	private Profile profile;

	@Column(name = " id_keycloak")
	private String idKeycloak;

	@Column(name = " username")
	private String username;

	@Column(name = " name")
	private String name;

	@Column(name = " famillyName")
	private String famillyName;

	@PostPersist
	public void postPersist() {
		String Uuid = Utils.getHashedUuid(getCreateDateTime(), getId());
		this.setUuid(Uuid);
		String code = Utils.generateHash();
		this.setCode(code);
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
