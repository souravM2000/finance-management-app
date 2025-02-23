package com.kshirsa.trackingservice.service.declaration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kshirsa.coreservice.exception.CustomException;
import com.kshirsa.trackingservice.dto.TrackingFilter;
import com.kshirsa.trackingservice.dto.response.CategoryResponse;
import com.kshirsa.trackingservice.dto.response.TrackingFilterRes;
import com.kshirsa.trackingservice.dto.response.ViewTransaction;
import com.kshirsa.trackingservice.entity.enums.SortBy;
import com.kshirsa.trackingservice.entity.enums.TransactionType;
import com.kshirsa.trackingservice.entity.Transactions;

import java.util.List;

public interface TrackingGetService {
    List<ViewTransaction> getRecentTransaction();

    List<CategoryResponse> getCategory(TransactionType type);

    Object getHashTags() throws JsonProcessingException;

    List<ViewTransaction> getTransaction(TrackingFilter filter, Integer pageNumber, Integer transactionPerPage, SortBy sortBy);

    TrackingFilterRes getTransactionFilter() throws JsonProcessingException;

    Transactions getSingleTransaction(String transactionId) throws CustomException;
}
