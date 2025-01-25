package com.kshirsa.trackingservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TrackingFilter {

    @Schema(name = "hashTag", type = "array[String]", description = "Array of selected hashtag filters", example = "tv, trip")
    private String[] hashTag;

    @Schema(name = "transactionType", type = "array[String]", description = "Array of selected transaction type filters", example = "EXPENSE, INCOME, LOAN")
    private String[] transactionType;

    @Schema(name = "paymentMode", type = "array[String]", description = "Array of selected payment mode filters", example = "CASH,DEBIT_CARD, CREDIT_CARD")
    private String[] paymentMode;

    @Schema(name = "category", type = "array[String]", description = "Array of selected category filters", example = "Electronics, Clothing")
    private String[] category;

    @Schema(name = "transactionBefore", description = "Filter for transactions that occurred before this date and time", example = "15-01-2025 14:30:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime transactionBefore;

    @Schema(name = "transactionAfter", description = "Filter for transactions that occurred after this date and time", example = "01-01-2025 00:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime transactionAfter;

    @Schema(name = "amountMin", description = "Minimum transaction amount filter", example = "50.00")
    private Double amountMin;

    @Schema(name = "amountMax", description = "Maximum transaction amount filter", example = "1000.00")
    private Double amountMax;

}

