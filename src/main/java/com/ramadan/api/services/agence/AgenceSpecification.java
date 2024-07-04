package com.ramadan.api.services.agence;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.ramadan.api.dto.agance.model.AgenceFilterRequestDto;
import com.ramadan.api.entity.agence.Agency;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class AgenceSpecification implements Specification<Agency> {
    private static final long serialVersionUID = 1L;

    private AgenceFilterRequestDto searchDTO;
    private String companyUuid;

    public AgenceSpecification(AgenceFilterRequestDto searchDTO) {
        this.searchDTO = searchDTO;
    }

    public AgenceSpecification(String companyUuid) {
        this.companyUuid = companyUuid;
    }

    @Override
    public Predicate toPredicate(Root<Agency> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        if (companyUuid != null) {
            predicates.add(cb.equal(root.get("company").get("uuid"), companyUuid));
        }

        if (searchDTO != null) {
            if (searchDTO.getCode() != null) {
                predicates.add(cb.equal(root.get("code"), searchDTO.getCode()));
            }
            if (searchDTO.getDescription() != null) {
                predicates.add(cb.like(root.get("description"), "%" + searchDTO.getDescription() + "%"));
            }
            if (searchDTO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + searchDTO.getName() + "%"));
            }
            if (searchDTO.getCapital() != null) {
                predicates.add(cb.equal(root.get("capital"), searchDTO.getCapital()));
            }
            if (searchDTO.getRegistreCommercial() != null) {
                predicates.add(cb.equal(root.get("registreCommercial"), searchDTO.getRegistreCommercial()));
            }
            if (searchDTO.getIdentifiantFiscal() != null) {
                predicates.add(cb.equal(root.get("identifiantFiscal"), searchDTO.getIdentifiantFiscal()));
            }
            if (searchDTO.getTravauxPublic() != null) {
                predicates.add(cb.like(root.get("travauxPublic"), "%" + searchDTO.getTravauxPublic() + "%"));
            }
            if (searchDTO.getCodeVille() != null) {
                predicates.add(cb.equal(root.get("codeVille"), searchDTO.getCodeVille()));
            }
            if (searchDTO.getCnss() != null) {
                predicates.add(cb.equal(root.get("cnss"), searchDTO.getCnss()));
            }
            if (searchDTO.getWeb() != null) {
                predicates.add(cb.like(root.get("web"), "%" + searchDTO.getWeb() + "%"));
            }
            if (searchDTO.getRang() != null) {
                predicates.add(cb.equal(root.get("rang"), searchDTO.getRang()));
            }
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
