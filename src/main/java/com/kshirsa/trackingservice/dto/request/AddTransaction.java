package com.kshirsa.trackingservice.dto.request;

import com.kshirsa.trackingservice.entity.PaymentMode;
import com.kshirsa.trackingservice.entity.TransactionType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class AddTransaction {

    public record AddLoanDetails(LocalDate expectedPaymentDate, String transactingParty) {}

    @NotNull
    private Integer amount;
    @NotNull
    private PaymentMode paymentMode;
    private String note;
    @NotNull
    private TransactionType transactionType;
    @NotNull
    private LocalDateTime transactionTime;
    @NotNull
    private String categoryId;
    private Boolean isRecurring;
    private List<String> tagIds;
    private AddLoanDetails loanDetails;
}
