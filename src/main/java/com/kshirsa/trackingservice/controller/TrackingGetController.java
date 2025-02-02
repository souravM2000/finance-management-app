package com.kshirsa.trackingservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kshirsa.coreservice.BaseConstants;
import com.kshirsa.coreservice.exception.CustomException;
import com.kshirsa.trackingservice.dto.TrackingFilter;
import com.kshirsa.trackingservice.dto.response.TrackingFilterRes;
import com.kshirsa.trackingservice.dto.response.ViewTransaction;
import com.kshirsa.trackingservice.entity.Category;
import com.kshirsa.trackingservice.entity.Transactions;
import com.kshirsa.trackingservice.entity.enums.TransactionType;
import com.kshirsa.trackingservice.service.declaration.TrackingGetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = BaseConstants.ROOT_PATH +"/track/get", produces = "application/json")
@Validated
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "4. Tracking Get Controller", description = "APIs for getting money tracking data")
public class TrackingGetController {

    private final TrackingGetService trackingGetService;

    @Operation(summary = "Get all categories for a transaction type")
    @GetMapping("/category")
    public List<Category> getCategory(TransactionType type) {
        return trackingGetService.getCategory(type);
    }

    @Operation(summary = "Get a transaction by id")
    @GetMapping("transaction/{transactionId}")
    public Transactions getTransaction(@PathVariable String transactionId) throws CustomException {
        return trackingGetService.getSingleTransaction(transactionId);
    }

    @Operation(summary = "Get last 10 transactions")
    @GetMapping("/transaction/recent")
    public List<ViewTransaction> getRecentTransaction() {
        return trackingGetService.getRecentTransaction();
    }

    @Operation(summary = "Get transactions based on filters")
    @Parameter(name = "sortBy", description = "Sort results by 'Latest', 'Oldest', 'AmountHighToLow', 'AmountLowToHigh'")
    @GetMapping("/transaction")
    public List<ViewTransaction> getTransactions(@ParameterObject @ModelAttribute TrackingFilter filter,
                                                 @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
                                                 @RequestParam(required = false, defaultValue = "6") Integer transactionPerPage,
                                                 @RequestParam(required = false, defaultValue = "Latest") String sortBy) {
        return trackingGetService.getTransaction(filter,pageNumber,transactionPerPage,sortBy);
    }

    @Operation(summary = "Get transaction filters")
    @GetMapping("/transaction/filters")
    public TrackingFilterRes getTransactionFilters() throws JsonProcessingException {
        return trackingGetService.getTransactionFilter();
    }

    @Operation(summary = "Get all unique hashtags for the user")
    @GetMapping("/hashtags")
    public Object getHashTags() {
        return trackingGetService.getHashTags();
    }
}
