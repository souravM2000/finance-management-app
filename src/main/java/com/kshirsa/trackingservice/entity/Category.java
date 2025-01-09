package com.kshirsa.trackingservice.entity;

import com.kshirsa.trackingservice.dto.request.AddCategory;
import com.kshirsa.utility.IdGenerator;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Category {

    @Id
    private String categoryId;
    private String categoryName;
    private String description;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private String createdBy;

    public Category(AddCategory addCategory) {
        this.categoryId = IdGenerator.generateCategoryId();
        this.categoryName = addCategory.categoryName();
        this.description = addCategory.description();
        this.transactionType = addCategory.transactionType();
    }
}
