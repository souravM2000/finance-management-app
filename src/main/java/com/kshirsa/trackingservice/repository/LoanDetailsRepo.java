package com.kshirsa.trackingservice.repository;

import com.kshirsa.trackingservice.entity.LoanDetails;
import org.springframework.data.repository.ListCrudRepository;

public interface LoanDetailsRepo extends ListCrudRepository<LoanDetails,String> {
}
