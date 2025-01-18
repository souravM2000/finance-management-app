package com.kshirsa.trackingservice.repository;

import com.kshirsa.trackingservice.entity.HashTags;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Set;

public interface HashTagRepo extends ListCrudRepository<HashTags, String> {
    Set<HashTags> findHashTagsByUserId(String userId);
}
