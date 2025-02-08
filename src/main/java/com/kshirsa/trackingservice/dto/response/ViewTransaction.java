package com.kshirsa.trackingservice.dto.response;

import com.kshirsa.trackingservice.entity.enums.PaymentMode;
import com.kshirsa.trackingservice.entity.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewTransaction {

    private String transactionId;
    private Double amount;
    private PaymentMode paymentMode;
    private String note;
    private TransactionType transactionType;
    private LocalDateTime transactionTime;
    private String category;
    private Set<String> tags;
    private Boolean isLoanSettled;
    private Double outstandingLoanAmount;
}
