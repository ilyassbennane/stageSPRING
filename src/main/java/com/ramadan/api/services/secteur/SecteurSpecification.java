package com.ramadan.api.services.secteur;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.ramadan.api.dto.costumer.model.CostumerFilterRequestDto;
import com.ramadan.api.dto.secteur.model.SectorSearchFilterDto;
import com.ramadan.api.entity.agence.Sector;
import com.ramadan.api.entity.costumer.Costumer;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class SecteurSpecification  implements Specification<Sector>{
	private static final long serialVersionUID = 1L;
	
	private SectorSearchFilterDto searchDTO;

    public SecteurSpecification(SectorSearchFilterDto searchDTO) {
        this.searchDTO = searchDTO;
    }
    @Override
    public Predicate toPredicate(Root<Sector> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (searchDTO.getCode() != null) {
            predicates.add(cb.like(root.get("code"), "%" + searchDTO.getCode() + "%"));
        }
        if (searchDTO.getDescription() != null) {
            predicates.add(cb.like(root.get("description"), "%" + searchDTO.getDescription() + "%"));
        }
        if (searchDTO.getRang() != null) {
            predicates.add(cb.equal(root.get("rang"), searchDTO.getRang()));
        }
        if (searchDTO.getName() != null) {
            predicates.add(cb.like(root.get("name"), "%" + searchDTO.getName() + "%"));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }

}
