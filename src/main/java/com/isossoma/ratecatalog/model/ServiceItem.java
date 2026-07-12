package com.isossoma.ratecatalog.model;

import com.isossoma.ratecatalog.enums.ParameterType;
import com.isossoma.shared.model.enums.RecordStatus;
import com.isossoma.shared.model.entities.Auditable;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.CascadeType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.AccessLevel;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "service-item")
public class ServiceItem extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "parameter_type")
    private ParameterType parameterType;

    private String note;

    @Column(name = "status", nullable = true)
    private RecordStatus status = RecordStatus.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_category_id")
    private ServiceCategory serviceCategory;

    @OneToMany(
            mappedBy = "serviceItem",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<ItemSupplier> suppliers = new ArrayList<>();

    public ServiceItem(String description, ParameterType parameterType, String note, ServiceCategory category) {
        this.description = description;
        this.parameterType = parameterType;
        this.note = note;
        this.serviceCategory = category;
    }

    public void update(String description, String note, ParameterType parameterType, ServiceCategory category) {
        if (status == RecordStatus.INACTIVE) {
            throw new RuntimeException("Inactive items cannot be modified");
        }

        this.description = description;
        this.note = note;
        this.serviceCategory = category;
        this.parameterType = parameterType;
    }

    public void deactivate() {
        if (this.status == RecordStatus.INACTIVE) {
            return;
        }

        this.status = RecordStatus.INACTIVE;

        suppliers.forEach(ItemSupplier::deactivate);
    }

    public void reactivate() {
        if (this.status == RecordStatus.ACTIVE) {
            return;
        }

        this.status = RecordStatus.ACTIVE;
        suppliers.forEach(ItemSupplier::reactivate);
    }

    public ItemSupplier addSupplier(
            ServiceSupplier supplier,
            BigDecimal price,
            String methodology,
            String accreditation,
            String location
    ) {

        ItemSupplier itemSupplier = new ItemSupplier(
                this,
                supplier,
                price,
                methodology,
                accreditation,
                location
        );

        suppliers.add(itemSupplier);

        return itemSupplier;
    }
}