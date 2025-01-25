package com.kshirsa.trackingservice.controller;

import com.kshirsa.coreservice.BaseConstants;
import com.kshirsa.coreservice.exception.CustomException;
import com.kshirsa.trackingservice.dto.request.AddCategory;
import com.kshirsa.trackingservice.dto.request.AddTransaction;
import com.kshirsa.trackingservice.entity.Category;
import com.kshirsa.trackingservice.entity.Transactions;
import com.kshirsa.trackingservice.service.declaration.TrackingAddService;
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
@RequestMapping(value = BaseConstants.ROOT_PATH +"/track/add", produces = "application/json")
@Validated
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "3. Tracking Controller", description = "APIs for money tracking")
public class TrackingController {

    private final TrackingAddService trackingAddService;

    @PostMapping("/category")
    public Category addCategory(@RequestBody AddCategory category) {
        return trackingAddService.addCategory(category);
    }

    @PostMapping("/transaction")
    public Transactions addTransaction(@RequestBody @Valid AddTransaction transaction) throws CustomException {
        return trackingAddService.addTransaction(transaction);
    }
}
