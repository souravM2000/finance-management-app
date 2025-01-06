package com.kshirsa.trackingservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String categoryId;
    private String categoryName;
    private String description;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private String createdBy;
    private String categoryColour;
    private String iconId;
}
