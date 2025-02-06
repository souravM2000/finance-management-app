package com.kshirsa.trackingservice.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kshirsa.trackingservice.entity.enums.PaymentMode;
import com.kshirsa.trackingservice.entity.enums.TransactionType;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class UpdateTransaction {

    public record UpdateLoanDetails(
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy") LocalDate expectedPaymentDate,
            String transactingParty) {
    }

    @NotNull
    private String transactionId;
    private Double amount;
    private PaymentMode paymentMode;
    private String note;
    private TransactionType transactionType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime transactionTime;
    private String categoryId;
    private Boolean isRecurring;
    @Nullable
    private UpdateLoanDetails loanDetails;
}
