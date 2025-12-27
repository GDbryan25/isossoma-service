package com.isossoma.quotation.models;

import com.isossoma.customer.models.Customer;
import com.isossoma.shared.model.Auditable;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Table(name = "quotations")
public class Quotation extends Auditable {
    private Long id;
    private String quotationCode;
    private String projectName;
    private String projectPlace;
    private LocalDate projectDate;
    private String currency;
    private String issueDate;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @OneToMany(
            mappedBy = "quotation",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<QuotationDetail> quotationDetails;
}