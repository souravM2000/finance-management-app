package com.kshirsa.trackingservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class LoanDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String loanId;
    private LocalDate expectedPaymentDate;
    @OneToOne()
    @JoinColumn(name = "transaction")
    private Transactions transaction;
    private String transactingParty;
    private Integer paidAmount;
    private Integer outstandingAmount;
    private Boolean isSettled;
    @OneToMany(mappedBy = "loanDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LoanRepayment> loanRepayments;
}
