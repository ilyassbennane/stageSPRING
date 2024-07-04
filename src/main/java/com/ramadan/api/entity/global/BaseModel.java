package com.ramadan.api.entity.global;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.annotation.PreDestroy;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "uuid", nullable = true)
    private String uuid;

    @Column(name = "code", nullable = true)
    private String code;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "name", nullable = true)
    private String name ;

    @Column(name = "is_delete", nullable = true,columnDefinition = "boolean default false")
    private boolean isDelete;

    @Column(name = "is_statut", nullable = true,columnDefinition = "boolean default true")
    private boolean isStatut;

    @Column(name = "is_active", nullable = true,columnDefinition = "boolean default true")
    private boolean isActive;
    
    @Column(name = "is_valid", nullable = true,columnDefinition = "boolean default false")
    private boolean isValid;

    @CreationTimestamp
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;

	@PrePersist
	public void prePersist() {
		this.isDelete = false;
		this.isStatut = true;
		this.isActive = true;
		this.isValid = false;
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

	}
}
