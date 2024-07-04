package com.ramadan.api.services.costumer.familyCostumer;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.ramadan.api.dto.familyCostumer.model.FamilyCostumerSearchRequestDto;
import com.ramadan.api.entity.costumer.FamilyCostumer;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
public class FamilyCostumerSpecification implements Specification<FamilyCostumer>{
	private static final long serialVersionUID = 1L;

	private FamilyCostumerSearchRequestDto searchDTO;

    public FamilyCostumerSpecification(FamilyCostumerSearchRequestDto searchDTO) {
        this.searchDTO = searchDTO;
    }

    @Override
    public Predicate toPredicate(Root<FamilyCostumer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (searchDTO.getName() != null) {
            predicates.add(cb.like(root.get("name"), "%" + searchDTO.getName() + "%"));
        }
        if (searchDTO.getDescription() != null)  {
            predicates.add(cb.like(root.get("description"), "%" + searchDTO.getDescription() + "%"));
        }

        if (Objects.nonNull(searchDTO.getName())) {
            predicates.add(cb.like(root.get("name"), searchDTO.getName()));
        }
        if (Objects.nonNull(searchDTO.getDescription())) {
            predicates.add(cb.like(root.get("description"), searchDTO.getDescription()));
        }
        if (Objects.nonNull(searchDTO.getRang())) {
//            String rangValue = searchDTO.getRang().toString();
//            predicates.add(cb.like(cb.toString(root.get("rang")), "%" + rangValue + "%"));
        	predicates.add(cb.equal(root.get("rang"), searchDTO.getRang()));
        }


        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
