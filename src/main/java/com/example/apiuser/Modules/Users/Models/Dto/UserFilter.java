package com.example.apiuser.Modules.Users.Models.Dto;

import com.example.apiuser.Modules.Users.Models.User;
import org.springframework.data.jpa.domain.Specification;
import lombok.Data;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserFilter {

    private String search;

    public Specification<User> toSpecification() {
        return (userEntity, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (search != null && !search.isBlank()) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(userEntity.get("name")), "%" + search.toLowerCase() + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(userEntity.get("email")), "%" + search.toLowerCase() + "%")
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}