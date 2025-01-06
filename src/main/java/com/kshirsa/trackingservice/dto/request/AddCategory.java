package com.kshirsa.trackingservice.dto.request;

import com.kshirsa.trackingservice.entity.TransactionType;
import jakarta.validation.constraints.NotNull;

public record AddCategory(@NotNull String categoryName,String description,
                          @NotNull TransactionType transactionType, String categoryColour, String iconId) {
}
