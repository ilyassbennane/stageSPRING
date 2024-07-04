package com.ramadan.api.services.costumer;
import java.util.ArrayList;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.ramadan.api.dto.costumer.model.CostumerFilterRequestDto;
import com.ramadan.api.entity.costumer.Costumer;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class CostumerSpecification  implements Specification<Costumer>{
	private static final long serialVersionUID = 1L;
	
	private CostumerFilterRequestDto searchDTO;

    public CostumerSpecification(CostumerFilterRequestDto searchDTO) {
        this.searchDTO = searchDTO;
    }
    @Override
    public Predicate toPredicate(Root<Costumer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (searchDTO.getFamilyCustomerUuid() != null) {
            predicates.add(cb.equal(root.get("familyCustomerUuid"), searchDTO.getFamilyCustomerUuid()));
        }
        if (searchDTO.getName() != null) {
            predicates.add(cb.like(root.get("name"), "%" + searchDTO.getName() + "%"));
        }
        if (searchDTO.getSectorUuid() != null) {
            predicates.add(cb.equal(root.get("sectorUuid"), "%" + searchDTO.getSectorUuid()));
        }
        if (searchDTO.getSectorUuid() != null) {
            predicates.add(cb.equal(root.get("sectorUuid"), searchDTO.getSectorUuid()));
        }
        if (searchDTO.getRaisonSociale1() != null) {
            predicates.add(cb.like(root.get("raisonSociale1"), "%" + searchDTO.getRaisonSociale1() + "%"));
        }
        if (searchDTO.getRaisonSociale2() != null) {
            predicates.add(cb.like(root.get("raisonSociale2"), "%" + searchDTO.getRaisonSociale2() + "%"));
        }
        if (searchDTO.getCodeTva() != null) {
            predicates.add(cb.like(root.get("codeTva"), "%" + searchDTO.getCodeTva() + "%"));
        }
        if (searchDTO.getCodeGender() != null) {
            predicates.add(cb.like(root.get("codeGender"), "%" + searchDTO.getCodeGender() + "%"));
        }
        if (searchDTO.getBarcode() != null) {
            predicates.add(cb.like(root.get("barcode"), "%" + searchDTO.getBarcode() + "%"));
        }
        if (searchDTO.getRang() != null) {
            predicates.add(cb.equal(root.get("rang"), searchDTO.getRang()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }


}
