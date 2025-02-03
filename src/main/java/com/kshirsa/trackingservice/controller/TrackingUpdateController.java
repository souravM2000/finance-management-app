package com.kshirsa.trackingservice.controller;

import com.kshirsa.coreservice.BaseConstants;
import com.kshirsa.coreservice.exception.CustomException;
import com.kshirsa.trackingservice.dto.request.UpdateCategory;
import com.kshirsa.trackingservice.dto.request.UpdateTransaction;
import com.kshirsa.trackingservice.entity.Category;
import com.kshirsa.trackingservice.entity.Transactions;
import com.kshirsa.trackingservice.service.declaration.TrackingUpdateService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = BaseConstants.ROOT_PATH +"/track/update", produces = "application/json")
@Validated
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "5. Tracking Update Controller", description = "APIs for updating money tracking data")
public class TrackingUpdateController {

    private final TrackingUpdateService updateService;

    @PutMapping("/category")
    public Category updateCategory(@RequestBody UpdateCategory category) throws CustomException {
        return updateService.updateCategory(category);
    }

    @PutMapping("/transaction")
    public Transactions updateTransaction(@RequestBody UpdateTransaction updateTransaction) throws CustomException {
        return updateService.updateTransaction(updateTransaction);
    }
}
