package com.kshirsa.trackingservice.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record UpdateLoanRepayment(String loanRepaymentId,
                                  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
                                  LocalDateTime paymentDate,
                                  Double amount,
                                  String note) {
}
