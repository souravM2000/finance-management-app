package com.kshirsa.trackingservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class LoanRepayment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String loanRepaymentId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDateTime paymentDate;
    private Integer amount;
    private String note;
    @ManyToOne
    @JoinColumn(name = "loan_id")
    private LoanDetails loanDetails;
}
