package com.isossoma.ratecatalog.model;

import com.isossoma.shared.model.enums.RecordStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "item_supplier")
public class ItemSupplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal price;

    private String methodology;

    private String accreditation;

    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private ServiceItem serviceItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private ServiceSupplier supplier;

    @Column(name = "status", nullable = true)
    private RecordStatus status = RecordStatus.ACTIVE;

    public ItemSupplier(
            ServiceItem serviceItem,
            ServiceSupplier supplier,
            BigDecimal price,
            String methodology,
            String accreditation,
            String location
    ) {
        this.serviceItem = serviceItem;
        this.supplier = supplier;
        this.price = price;
        this.methodology = methodology;
        this.accreditation = accreditation;
        this.location = location;
    }

    public void update(
            ServiceSupplier supplier,
            BigDecimal price,
            String methodology,
            String accreditation,
            String location
    ) {

        if (status == RecordStatus.INACTIVE) {
            throw new IllegalStateException("Inactive supplier cannot be modified");
        }

        this.supplier = supplier;
        this.price = price;
        this.methodology = methodology;
        this.accreditation = accreditation;
        this.location = location;
    }

    public void deactivate() {
        if (status == RecordStatus.INACTIVE) {
            return;
        }

        this.status = RecordStatus.INACTIVE;
    }

    public void reactivate() {
        if (status == RecordStatus.ACTIVE) {
            return;
        }

        this.status = RecordStatus.ACTIVE;
    }
}
