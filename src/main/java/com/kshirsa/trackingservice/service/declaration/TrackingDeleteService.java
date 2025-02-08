package com.kshirsa.trackingservice.service.declaration;

public interface TrackingDeleteService {
    void deleteCategory(String categoryId);

    void deleteTransaction(String transactionId);
}
