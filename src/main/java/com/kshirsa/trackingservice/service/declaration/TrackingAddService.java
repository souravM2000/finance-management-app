package com.kshirsa.trackingservice.service.declaration;

import com.kshirsa.coreservice.exception.CustomException;
import com.kshirsa.trackingservice.dto.request.AddCategory;
import com.kshirsa.trackingservice.dto.request.AddTransaction;
import com.kshirsa.trackingservice.entity.Category;
import com.kshirsa.trackingservice.entity.Transactions;
import jakarta.validation.Valid;

public interface TrackingAddService {

    Category addCategory(AddCategory category);

    Transactions addTransaction(@Valid AddTransaction transaction) throws CustomException;
}
