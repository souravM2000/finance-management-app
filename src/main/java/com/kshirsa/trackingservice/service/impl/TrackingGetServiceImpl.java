package com.kshirsa.trackingservice.service.impl;

import com.kshirsa.trackingservice.entity.Category;
import com.kshirsa.trackingservice.entity.TransactionType;
import com.kshirsa.trackingservice.entity.Transactions;
import com.kshirsa.trackingservice.repository.CategoryRepo;
import com.kshirsa.trackingservice.repository.LoanDetailsRepo;
import com.kshirsa.trackingservice.repository.TransactionRepo;
import com.kshirsa.trackingservice.service.declaration.TrackingGetService;
import com.kshirsa.userservice.service.declaration.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackingGetServiceImpl implements TrackingGetService {

    private final CategoryRepo categoryRepo;
    private final UserDetailsService userDetailsService;
    private final TransactionRepo transactionRepo;
    private final LoanDetailsRepo loanDetailsRepo;

    @Override
    public List<Transactions> getRecentTransaction() {
        Pageable pageable = PageRequest.of(0,10);
        return transactionRepo.findAllByUserDetails_UserId(userDetailsService.getUser(),pageable);
    }

    @Override
    public List<Category> getCategory(TransactionType type) {
        return categoryRepo.getAllCategory(userDetailsService.getUser(), type.name());
    }
}
