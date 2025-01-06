package com.kshirsa.trackingservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kshirsa.userservice.entity.UserDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String transactionId;
    private Integer amount;
    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;
    private String note;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private LocalDateTime transactionTime;
    private Boolean isRecurring;
    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @OneToOne(mappedBy = "transaction")
    private LoanDetails loanDetails;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> tags;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserDetails userDetails;
}

