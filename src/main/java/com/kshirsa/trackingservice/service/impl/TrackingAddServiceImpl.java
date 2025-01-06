package com.kshirsa.trackingservice.service.impl;

import com.kshirsa.coreservice.exception.CustomException;
import com.kshirsa.trackingservice.dto.request.AddCategory;
import com.kshirsa.trackingservice.dto.request.AddTransaction;
import com.kshirsa.trackingservice.entity.Category;
import com.kshirsa.trackingservice.entity.Transactions;
import com.kshirsa.trackingservice.repository.CategoryRepo;
import com.kshirsa.trackingservice.service.declaration.TrackingAddService;
import com.kshirsa.userservice.service.declaration.UserDetailsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class TrackingAddServiceImpl implements TrackingAddService {

    private final CategoryRepo categoryRepo;
    private final ModelMapper modelMapper;
    private final UserDetailsService userDetailsService;


    @Override
    public Category addCategory(AddCategory categoryReq) {
        Category category = modelMapper.map(categoryReq, Category.class);
        category.setCreatedBy(userDetailsService.getUser());
        return categoryRepo.save(category);
    }

    @Override
    public Transactions addTransaction(AddTransaction transaction) throws CustomException {
//        Category category=categoryRepo.findById(transaction.getCategoryId())
//                .orElseThrow(()->new CustomException(ErrorCode.INVALID_CATEGORY.name()));
//        if(!category.getTransactionType().equals(transaction.getTransactionType()))
//            throw new CustomException(ErrorCode.WRONG_TRANSACTION_TYPE.name());
//



        return modelMapper.map(transaction, Transactions.class);
    }
}
