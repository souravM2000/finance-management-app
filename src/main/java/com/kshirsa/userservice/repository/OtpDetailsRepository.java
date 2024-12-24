package com.kshirsa.userservice.repository;

import com.kshirsa.userservice.entity.OtpDetails;
import org.springframework.data.repository.ListCrudRepository;

public interface OtpDetailsRepository extends ListCrudRepository<OtpDetails, String> {
    void deleteByEmail(String email);
}
