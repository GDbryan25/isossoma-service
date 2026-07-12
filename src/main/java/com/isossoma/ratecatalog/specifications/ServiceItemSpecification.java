package com.isossoma.ratecatalog.specifications;

import com.isossoma.ratecatalog.dto.filters.ServiceItemFilter;
import com.isossoma.ratecatalog.model.ItemSupplier;
import com.isossoma.ratecatalog.model.ServiceCategory;
import com.isossoma.ratecatalog.model.ServiceItem;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class ServiceItemSpecification {
    public static Specification<ServiceItem> withFilters(ServiceItemFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(filter.status() != null) {
                predicates.add(cb.equal(root.get("status"), filter.status()));
            }

            if(filter.description() != null && !filter.description().isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("description")),
                                "%" + filter.description().toLowerCase() + "%"
                        )
                );
            }

            if(filter.parameterType() != null) {
                predicates.add(cb.equal(root.get("parameterType"), filter.parameterType()));
            }

            if(filter.categoryId() != null) {
                Join<ServiceItem, ServiceCategory> category = root.join("serviceCategory");

                predicates.add(cb.equal(category.get("id"), filter.categoryId()));
            }

            if(filter.supplierId() != null) {
                Join<ServiceItem, ItemSupplier> supplier = root.join("suppliers");

                predicates.add(
                        cb.equal(
                                supplier
                                        .get("supplier")
                                        .get("id"),
                                filter.supplierId()
                        )
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}