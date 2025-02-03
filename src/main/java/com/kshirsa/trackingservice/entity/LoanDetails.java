package com.kshirsa.trackingservice.entity;

import com.kshirsa.trackingservice.dto.request.AddTransaction;
import com.kshirsa.utility.IdGenerator;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class LoanDetails {

    @Id
    private String loanId;
    private LocalDate expectedPaymentDate;
    @SuppressWarnings("JpaDataSourceORMInspection")
    @OneToOne()
    @JoinColumn(name = "transaction")
    private Transactions transaction;
    private String transactingParty;
    private Double paidAmount;
    private Double outstandingAmount;
    private Boolean isSettled=false;
    @OneToMany(mappedBy = "loanDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LoanRepayment> loanRepayments;

    public LoanDetails(AddTransaction.AddLoanDetails loanDetails, Transactions transaction) {
        this.loanId = IdGenerator.generateLoanId();
        this.expectedPaymentDate = loanDetails.expectedPaymentDate();
        this.transactingParty = loanDetails.transactingParty();
        this.outstandingAmount = transaction.getAmount();
        this.paidAmount = 0.0;
        this.transaction = transaction;
    }
}
