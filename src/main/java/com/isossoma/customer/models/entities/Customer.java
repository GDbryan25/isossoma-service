package com.isossoma.customer.models.entities;

import com.isossoma.customer.models.enums.DocumentType;
import com.isossoma.customer.models.valueobjects.CustomerInformation;
import com.isossoma.shared.model.entities.Auditable;
import com.isossoma.shared.model.enums.RecordStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "customer")
public class Customer extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = true)
    private String contact;

    @Column(nullable = true)
    private String contactPosition;

    @Column(nullable = true)
    private String email;

    @Column(nullable = true)
    private String cellphone;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", nullable = false)
    private DocumentType documentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = true)
    private RecordStatus customerStatus;

    @Column(nullable = false, unique = true)
    private String documentNumber;

    @Column(nullable = true)
    private String observations;

    public Customer(CustomerInformation customer) {
        this.customerStatus = RecordStatus.ACTIVE;
        this.name = customer.name();
        this.address = customer.address();
        this.contact = customer.contact();
        this.contactPosition = customer.contactPosition();
        this.email = customer.email();
        this.cellphone = customer.cellphone();
        this.documentType = customer.documentType();
        this.documentNumber = customer.documentNumber();
        this.observations = customer.observations();
    }

    public void update(CustomerInformation customer) {
        if (customerStatus == RecordStatus.INACTIVE) {
            throw new RuntimeException("Inactive items cannot be modified");
        }

        this.name = customer.name();
        this.address = customer.address();
        this.contact = customer.contact();
        this.contactPosition = customer.contactPosition();
        this.email = customer.email();
        this.cellphone = customer.cellphone();
        this.documentType = customer.documentType();
        this.documentNumber = customer.documentNumber();
        this.observations = customer.observations();
    }

    public void deactivate() {
        if(this.customerStatus == RecordStatus.INACTIVE) return;

        this.customerStatus = RecordStatus.INACTIVE;
    }

    public void reactivate() {
        if (this.customerStatus == RecordStatus.ACTIVE) return;

        this.customerStatus = RecordStatus.ACTIVE;
    }
}