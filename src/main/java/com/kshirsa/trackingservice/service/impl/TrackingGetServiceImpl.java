package com.kshirsa.trackingservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kshirsa.coreservice.exception.CustomException;
import com.kshirsa.coreservice.exception.ErrorCode;
import com.kshirsa.trackingservice.dto.TrackingFilter;
import com.kshirsa.trackingservice.dto.response.TrackingFilterRes;
import com.kshirsa.trackingservice.dto.response.ViewTransaction;
import com.kshirsa.trackingservice.entity.Category;
import com.kshirsa.trackingservice.entity.Transactions;
import com.kshirsa.trackingservice.entity.enums.PaymentMode;
import com.kshirsa.trackingservice.entity.enums.SortBy;
import com.kshirsa.trackingservice.entity.enums.TransactionType;
import com.kshirsa.trackingservice.repository.CategoryRepo;
import com.kshirsa.trackingservice.repository.HashTagRepo;
import com.kshirsa.trackingservice.repository.TransactionRepo;
import com.kshirsa.trackingservice.service.declaration.TrackingGetService;
import com.kshirsa.userservice.service.declaration.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackingGetServiceImpl implements TrackingGetService {

    private final CategoryRepo categoryRepo;
    private final UserDetailsService userDetailsService;
    private final TransactionRepo transactionRepo;
    private final HashTagRepo hashTagRepo;
    private final ObjectMapper objectMapper;

    @Override
    public List<Category> getCategory(TransactionType type) {
        return categoryRepo.getAllCategory(userDetailsService.getUser(), type.name());
    }

    @Override
    public Object getHashTags() {
        return hashTagRepo.findHashTagsByUserId(userDetailsService.getUser());
    }

    @Override
    public List<ViewTransaction> getRecentTransaction() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Transactions> transactionsList = transactionRepo.findAllByUserDetails_UserId(userDetailsService.getUser(), pageable);
        if (transactionsList != null)
            return transactionsList.stream().map(TrackingGetServiceImpl::convertToViewTransaction).toList();
        return null;
    }

    @Override
    public List<ViewTransaction> getTransaction(TrackingFilter filter, Integer pageNumber, Integer transactionPerPage, String sortBy) {

        List<Transactions> transactions = null;
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, transactionPerPage);
        if (filter == null)
            filter = new TrackingFilter();

        if (SortBy.Latest.name().equalsIgnoreCase(sortBy)) {

            transactions = transactionRepo.findTransactions(filter.getCategory(), filter.getPaymentMode(),
                    filter.getTransactionType(), filter.getAmountMax(), filter.getAmountMin(), filter.getTransactionAfter(),
                    filter.getTransactionBefore(), filter.getHashTag(), userDetailsService.getUser(),
                    pageRequest.withSort(SortBy.Latest.getSortDirection(), SortBy.Latest.getSortBy()));

        } else if (SortBy.Oldest.name().equalsIgnoreCase(sortBy)) {

            transactions = transactionRepo.findTransactions(filter.getCategory(), filter.getPaymentMode(),
                    filter.getTransactionType(), filter.getAmountMax(), filter.getAmountMin(), filter.getTransactionAfter(),
                    filter.getTransactionBefore(), filter.getHashTag(), userDetailsService.getUser(),
                    pageRequest.withSort(SortBy.Oldest.getSortDirection(), SortBy.Oldest.getSortBy()));

        } else if (SortBy.AmountHighToLow.name().equalsIgnoreCase(sortBy)) {

            transactions = transactionRepo.findTransactions(filter.getCategory(), filter.getPaymentMode(),
                    filter.getTransactionType(), filter.getAmountMax(), filter.getAmountMin(), filter.getTransactionAfter(),
                    filter.getTransactionBefore(), filter.getHashTag(), userDetailsService.getUser(),
                    pageRequest.withSort(SortBy.AmountHighToLow.getSortDirection(), SortBy.AmountHighToLow.getSortBy()));

        } else if (SortBy.AmountLowToHigh.name().equalsIgnoreCase(sortBy)) {

            transactions = transactionRepo.findTransactions(filter.getCategory(), filter.getPaymentMode(),
                    filter.getTransactionType(), filter.getAmountMax(), filter.getAmountMin(), filter.getTransactionAfter(),
                    filter.getTransactionBefore(), filter.getHashTag(), userDetailsService.getUser(),
                    pageRequest.withSort(SortBy.AmountLowToHigh.getSortDirection(), SortBy.AmountLowToHigh.getSortBy()));

        }
        if (transactions != null)
            return transactions.stream().map(TrackingGetServiceImpl::convertToViewTransaction).toList();

        return null;
    }

    @Override
    public TrackingFilterRes getTransactionFilter() throws JsonProcessingException {

        String userId = userDetailsService.getUser();
        String jsonString = (String) hashTagRepo.findHashTagsByUserId(userId).orElseGet(String::new);

        return TrackingFilterRes.builder()
                .categories(categoryRepo.getCategoryByUserId(userId))
                .paymentModes(new HashSet<>(List.of(PaymentMode.values())))
                .transactionTypes(new HashSet<>(List.of(TransactionType.values())))
                .hashtags(objectMapper.readValue(jsonString, List.class)).build();
    }

    @Override
    public Transactions getSingleTransaction(String transactionId) throws CustomException {
        return transactionRepo.findById(transactionId).orElseThrow(() -> new CustomException(ErrorCode.INVALID_TRANSACTION_ID.name()));
    }

    public static ViewTransaction convertToViewTransaction(Transactions transaction) {
        return new ViewTransaction(
                transaction.getTransactionId(), transaction.getAmount(), transaction.getPaymentMode(),
                transaction.getNote(), transaction.getTransactionType(), transaction.getTransactionTime(),
                transaction.getCategory().getCategoryName(), transaction.getTags(),
                transaction.getLoanDetails() != null ? transaction.getLoanDetails().getIsSettled() : null,
                transaction.getLoanDetails() != null ? transaction.getLoanDetails().getOutstandingAmount() : null);
    }
}
