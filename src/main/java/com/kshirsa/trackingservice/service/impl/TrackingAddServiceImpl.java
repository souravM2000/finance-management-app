package com.kshirsa.trackingservice.service.impl;

import com.kshirsa.coreservice.exception.CustomException;
import com.kshirsa.coreservice.exception.ErrorCode;
import com.kshirsa.trackingservice.dto.request.AddCategory;
import com.kshirsa.trackingservice.dto.request.AddTransaction;
import com.kshirsa.trackingservice.entity.Category;
import com.kshirsa.trackingservice.entity.LoanDetails;
import com.kshirsa.trackingservice.entity.TransactionType;
import com.kshirsa.trackingservice.entity.Transactions;
import com.kshirsa.trackingservice.repository.CategoryRepo;
import com.kshirsa.trackingservice.repository.LoanDetailsRepo;
import com.kshirsa.trackingservice.repository.TransactionRepo;
import com.kshirsa.trackingservice.service.declaration.TrackingAddService;
import com.kshirsa.userservice.entity.UserDetails;
import com.kshirsa.userservice.service.declaration.UserDetailsService;
import com.kshirsa.utility.IdGenerator;
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
    private final TransactionRepo transactionRepo;
    private final LoanDetailsRepo loanDetailsRepo;

    @Override
    public Category addCategory(AddCategory categoryReq) {
        Category category = modelMapper.map(categoryReq, Category.class);
        category.setCategoryId(IdGenerator.generateCategoryId());
        category.setCreatedBy(userDetailsService.getUser());
        return categoryRepo.save(category);
    }

    @Override
    public Transactions addTransaction(AddTransaction transactionDto) throws CustomException {

        Category category = categoryRepo.findById(transactionDto.getCategoryId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_CATEGORY.name()));        // Checking Category selected valid or not
        if (!category.getTransactionType().equals(transactionDto.getTransactionType()))
            throw new CustomException(ErrorCode.WRONG_TRANSACTION_TYPE.name());                    // Checking Transaction Type selected valid or not

        UserDetails userDetails = new UserDetails(userDetailsService.getUser());                    // Getting User Details.(Not from db as it's already validated via jwt)

        Transactions transaction = transactionRepo
                .save(Transactions.transactionsDtoToEntity(transactionDto, category, userDetails)); // Saving Transaction

        if (transaction.getTransactionType().equals(TransactionType.LOAN)) {
            if (transactionDto.getLoanDetails() == null)
                throw new CustomException(ErrorCode.LOAN_DETAILS_REQUIRED.name());
           transaction.setLoanDetails(loanDetailsRepo.save(new LoanDetails(transactionDto.getLoanDetails(), transaction)));     // Saving Loan Details
        }

        return transaction;
    }
}
