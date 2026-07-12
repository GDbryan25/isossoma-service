package com.isossoma.ratecatalog.specifications;

import com.isossoma.ratecatalog.dto.filters.ItemSupplierPageableFilter;
import com.isossoma.ratecatalog.model.ItemSupplier;
import com.isossoma.ratecatalog.model.ServiceSupplier;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public final class ItemSupplierSpecification {
    private ItemSupplierSpecification() {}

    public static Specification<ItemSupplier> filter(ItemSupplierPageableFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.itemId() != null) {
                predicates.add(
                        cb.equal(
                                root.get("serviceItem").get("id"),
                                filter.itemId()
                        )
                );
            }

            if (filter.status() != null) {
                predicates.add(
                        cb.equal(
                                root.get("status"),
                                filter.status()
                        )
                );
            }

            if (filter.name() != null) {
                Join<ItemSupplier, ServiceSupplier> supplier =
                        root.join("supplier", JoinType.INNER);

                predicates.add(
                        cb.like(
                                cb.lower(supplier.get("name")),
                                "%" + filter.name().toLowerCase() + "%"
                        )
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}