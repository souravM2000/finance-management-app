package com.kshirsa.trackingservice.service;

import com.kshirsa.trackingservice.entity.HashTags;
import com.kshirsa.trackingservice.repository.HashTagRepo;
import com.kshirsa.trackingservice.repository.TransactionRepo;
import com.kshirsa.userservice.service.declaration.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AsyncService {

    private final TransactionRepo transactionRepo;
    private final UserDetailsService userDetailsService;
    private final HashTagRepo hashTagRepo;

    @Async
    public void updateHashTags(){
        String userId = userDetailsService.getUser();
        hashTagRepo.save(new HashTags(userId,transactionRepo.findHashTagsByUserId(userId)));
    }
}
