package com.kshirsa.trackingservice.service.declaration;

import com.kshirsa.trackingservice.entity.Category;
import com.kshirsa.trackingservice.entity.TransactionType;
import com.kshirsa.trackingservice.entity.Transactions;

import java.util.List;

public interface TrackingGetService {
    List<Transactions> getRecentTransaction();

    List<Category> getCategory(TransactionType type);
}
