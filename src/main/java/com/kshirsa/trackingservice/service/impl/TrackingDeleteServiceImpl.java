package com.kshirsa.trackingservice.service.impl;

import com.kshirsa.coreservice.exception.CustomException;
import com.kshirsa.coreservice.exception.ErrorCode;
import com.kshirsa.trackingservice.entity.Transactions;
import com.kshirsa.trackingservice.repository.CategoryRepo;
import com.kshirsa.trackingservice.repository.LoanDetailsRepo;
import com.kshirsa.trackingservice.repository.LoanRepaymentRepo;
import com.kshirsa.trackingservice.repository.TransactionRepo;
import com.kshirsa.trackingservice.service.AsyncService;
import com.kshirsa.trackingservice.service.declaration.TrackingDeleteService;
import com.kshirsa.trackingservice.service.declaration.TrackingUpdateService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Transactional(rollbackOn = DataIntegrityViolationException.class)
@RequiredArgsConstructor
public class TrackingDeleteServiceImpl implements TrackingDeleteService {

    private final CategoryRepo categoryRepo;
    private final TransactionRepo transactionRepo;
    private final LoanDetailsRepo loanDetailsRepo;
    private final LoanRepaymentRepo loanRepaymentRepo;
    private final AsyncService asyncService;

    @Override
    public void deleteCategory(String categoryId) {
        categoryRepo.deleteById(categoryId);
    }

    @Override
    public void deleteTransaction(String transactionId) {
        transactionRepo.deleteById(transactionId);
        asyncService.updateHashTags();
    }
}
