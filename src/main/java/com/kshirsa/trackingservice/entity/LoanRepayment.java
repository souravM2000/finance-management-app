package com.kshirsa.trackingservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Data
@Entity
public class LoanRepayment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String loanRepaymentId;
    @CreationTimestamp
    private LocalDateTime paymentDate;
    private Integer amount;
    private String note;
    @ManyToOne
    @JoinColumn(name = "loan_id")
    private LoanDetails loanDetails;
}
