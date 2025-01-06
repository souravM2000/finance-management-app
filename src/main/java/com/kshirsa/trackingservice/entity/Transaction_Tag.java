package com.kshirsa.trackingservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Transaction_Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String transactionId;
    private String tagId;
}
