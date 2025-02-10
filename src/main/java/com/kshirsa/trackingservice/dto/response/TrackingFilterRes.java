package com.kshirsa.trackingservice.dto.response;

import com.kshirsa.trackingservice.entity.enums.PaymentMode;
import com.kshirsa.trackingservice.entity.enums.TransactionType;
import lombok.Builder;

import java.util.Set;

@Builder
public record TrackingFilterRes(Set<PaymentMode> paymentModes, Set<TransactionType> transactionTypes, Set<String> categories,
                                Object hashtags) {
}
