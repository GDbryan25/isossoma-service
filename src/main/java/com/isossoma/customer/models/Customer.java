package com.isossoma.customer.models;

import com.isossoma.shared.model.Auditable;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Table(name = "customer")
public class Customer extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
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
    private CustomerStatus customerStatus = CustomerStatus.ACTIVE;
    @Column(nullable = false, unique = true)
    private String documentNumber;
    @Column(nullable = true)
    private String observations;
}