package com.kshirsa.trackingservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kshirsa.trackingservice.dto.request.AddTransaction;
import com.kshirsa.userservice.entity.UserDetails;
import com.kshirsa.utility.IdGenerator;
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
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transactions {

    @Id
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
    @OneToOne(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true)
    private LoanDetails loanDetails;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Set<String> tags;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserDetails userDetails;


    public static Transactions transactionsDtoToEntity(AddTransaction transaction, Category category, UserDetails userDetails) {
        Transactions transactions = new Transactions();
        transactions.setTransactionId(IdGenerator.generateTransactionId());
        transactions.setAmount(transaction.getAmount());
        transactions.setPaymentMode(transaction.getPaymentMode());
        transactions.setNote(transaction.getNote());
        transactions.setTransactionType(transaction.getTransactionType());
        transactions.setTransactionTime(transaction.getTransactionTime());
        transactions.setIsRecurring(transaction.getIsRecurring());
        transactions.setTags(transaction.getTags());
        transactions.setCategory(category);
        transactions.setUserDetails(userDetails);
        return transactions;
    }
}

