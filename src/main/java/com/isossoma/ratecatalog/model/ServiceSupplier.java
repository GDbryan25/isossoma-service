package com.isossoma.ratecatalog.model;

import com.isossoma.shared.model.enums.RecordStatus;
import com.isossoma.shared.model.entities.Auditable;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "service-supplier")
public class ServiceSupplier extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String note;

    @Column(name = "status", nullable = true)
    private RecordStatus status;

    @OneToMany(mappedBy = "supplier")
    private List<ItemSupplier> serviceItems;

    public ServiceSupplier(String name, String note) {
        this.name = name;
        this.note = note;
        this.status = RecordStatus.ACTIVE;
        this.serviceItems = new ArrayList<>();
    }

    public void update(String name, String note) {
        this.name = name;
        this.note = note;
    }

    public void deactivate() {
        if(status == RecordStatus.INACTIVE) return;

        this.status = RecordStatus.INACTIVE;
    }

    public void reactivate() {
        if (status == RecordStatus.ACTIVE) return;

        this.status = RecordStatus.ACTIVE;
    }
}