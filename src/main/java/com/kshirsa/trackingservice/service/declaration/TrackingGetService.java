package com.kshirsa.trackingservice.service.declaration;

import com.kshirsa.trackingservice.entity.Category;
import com.kshirsa.trackingservice.entity.HashTags;
import com.kshirsa.trackingservice.entity.TransactionType;
import com.kshirsa.trackingservice.entity.Transactions;

import java.util.List;
import java.util.Set;

public interface TrackingGetService {
    List<Transactions> getRecentTransaction();

    List<Category> getCategory(TransactionType type);

    Set<HashTags> getHashTags();
}
