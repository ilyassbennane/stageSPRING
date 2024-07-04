package com.ramadan.api.entity.file;

import org.hibernate.annotations.Where;

import com.ramadan.api.entity.global.BaseModel;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@NoArgsConstructor
@Table(name = "ns_file")
@Where(clause = "is_delete = false and is_statut = true")
public class File extends BaseModel {

	private static final long serialVersionUID = 1L;

}
