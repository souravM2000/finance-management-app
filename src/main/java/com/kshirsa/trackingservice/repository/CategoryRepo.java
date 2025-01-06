package com.kshirsa.trackingservice.repository;

import com.kshirsa.trackingservice.entity.Category;
import com.kshirsa.trackingservice.entity.TransactionType;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface CategoryRepo extends ListCrudRepository<Category,String> {
    Optional<Category> findCategoryByCategoryIdAndTransactionType(String categoryId, TransactionType transactionType);
}
