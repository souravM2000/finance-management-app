package com.kshirsa.trackingservice.repository;

import com.kshirsa.trackingservice.entity.Transactions;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface TransactionRepo extends ListCrudRepository<Transactions,String> {
    List<Transactions> findAllByUserDetails_UserId(String user, Pageable pageable);
}
