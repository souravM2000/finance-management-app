package com.kshirsa.trackingservice.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record UpdateLoanRepayment(String loanRepaymentId,
                                  LocalDateTime paymentDate,
                                  Double amount,
                                  String note) {
}
