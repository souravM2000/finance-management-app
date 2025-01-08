package com.kshirsa.trackingservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Category {

    @Id
    private String categoryId;
    private String categoryName;
    private String description;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private String createdBy;
}
