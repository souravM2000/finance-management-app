package com.kshirsa.trackingservice.controller;

import com.kshirsa.coreservice.BaseConstants;
import com.kshirsa.coreservice.exception.CustomException;
import com.kshirsa.trackingservice.dto.request.AddCategory;
import com.kshirsa.trackingservice.dto.request.AddLoanRepayment;
import com.kshirsa.trackingservice.dto.request.AddTransaction;
import com.kshirsa.trackingservice.entity.Category;
import com.kshirsa.trackingservice.entity.Transactions;
import com.kshirsa.trackingservice.service.declaration.TrackingAddService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = BaseConstants.ROOT_PATH + "/track/add", produces = "application/json")
@Validated
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "3. Tracking Add Controller", description = "APIs for adding money tracking data")
public class TrackingAddController {

    private final TrackingAddService trackingAddService;

    @Operation(summary = "Add a new category")
    @PostMapping("/category")
    public Category addCategory(@RequestBody AddCategory category) {
        return trackingAddService.addCategory(category);
    }

    @Operation(summary = "Add a new transaction")
    @PostMapping("/transaction")
    public Transactions addTransaction(@RequestBody @Valid AddTransaction transaction) throws CustomException {
        return trackingAddService.addTransaction(transaction);
    }

    @Operation(summary = "Add a new loan repayment")
    @PostMapping("/loan-repayment")
    public void addLoanRepayment(@RequestBody @Valid AddLoanRepayment loanRepayment) throws CustomException {
        trackingAddService.addLoanRepayment(loanRepayment);
    }
}
