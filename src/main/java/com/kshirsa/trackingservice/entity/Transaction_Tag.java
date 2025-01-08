package com.kshirsa.trackingservice.entity;

import lombok.Data;

@Data

public class Transaction_Tag {

    private String tagId;
    private String tagName;
    private String transactionId;
    private String userId;
}
