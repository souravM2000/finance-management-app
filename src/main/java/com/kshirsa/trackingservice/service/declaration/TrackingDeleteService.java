package com.kshirsa.trackingservice.service.declaration;

import com.kshirsa.coreservice.exception.CustomException;

public interface TrackingDeleteService {
    void deleteCategory(String categoryId);

    void deleteTransaction(String transactionId);

    void deleteHashTag(String transactionId, String hashTag) throws CustomException;
}
