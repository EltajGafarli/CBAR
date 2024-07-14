package org.example.cbarcurrencyproject.filter;

import org.example.cbarcurrencyproject.entity.Currency;
import org.springframework.data.jpa.domain.Specification;

public class CurrencySpecification {


    public static Specification<Currency> currencySpecificationName(String input) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + input + "%");
    }

    public static Specification<Currency> currencySpecificationCode(String input) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("code"), "%" + input + "%");
    }
}
