package com.ramadan.api.services.brand;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.ramadan.api.dto.brand.BrandSearchRequestDto;
import com.ramadan.api.entity.product.Brand;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
public class BrandSpecification implements Specification<Brand>{
	private static final long serialVersionUID = 1L;

	private BrandSearchRequestDto searchDTO;

    public BrandSpecification(BrandSearchRequestDto searchDTO) {
        this.searchDTO = searchDTO;
    }

    @Override
    public Predicate toPredicate(Root<Brand> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(searchDTO.getName())) {
            predicates.add(cb.like(root.get("name"), "%" + searchDTO.getName() + "%"));
        }
        if (Objects.nonNull(searchDTO.getDescription())) {
            predicates.add(cb.like(root.get("description"), "%" + searchDTO.getDescription() + "%"));
        }
        if (Objects.nonNull(searchDTO.getRang())) {
//            String rangValue = searchDTO.getRang().toString();
//            predicates.add(cb.like(cb.toString(root.get("rang")), "%" + rangValue + "%"));
        	predicates.add(cb.equal(root.get("rang"), searchDTO.getRang()));
        }


        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
