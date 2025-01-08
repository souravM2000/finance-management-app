package com.kshirsa.trackingservice.controller;

import com.kshirsa.coreservice.BaseConstants;
import com.kshirsa.trackingservice.entity.Category;
import com.kshirsa.trackingservice.entity.TransactionType;
import com.kshirsa.trackingservice.entity.Transactions;
import com.kshirsa.trackingservice.service.declaration.TrackingGetService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = BaseConstants.ROOT_PATH +"/track/get", produces = "application/json")
@Validated
@RequiredArgsConstructor
@Tag(name = "4. Tracking Get Controller", description = "APIs for getting money tracking data")
public class TrackingGetController {

    private final TrackingGetService trackingGetService;

    @GetMapping("/category")
    public List<Category> getCategory(TransactionType type) {
        return trackingGetService.getCategory(type);
    }

    @GetMapping("/v1/transaction")
    public List<Transactions> addRecentTransaction() {
        return trackingGetService.getRecentTransaction();
    }
}
