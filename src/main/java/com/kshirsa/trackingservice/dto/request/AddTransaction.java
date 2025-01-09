package com.kshirsa.trackingservice.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kshirsa.trackingservice.entity.PaymentMode;
import com.kshirsa.trackingservice.entity.TransactionType;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class AddTransaction {

    public record AddLoanDetails(@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")LocalDate expectedPaymentDate,
                                 String transactingParty) {}

    @NotNull
    private Integer amount;
    @NotNull
    private PaymentMode paymentMode;
    private String note;
    @NotNull
    private TransactionType transactionType;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime transactionTime;
    @NotNull
    private String categoryId;
    private Boolean isRecurring;
    private Set<String> tags;
    @Nullable
    private AddLoanDetails loanDetails;
}
