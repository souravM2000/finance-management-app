package com.kshirsa.trackingservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kshirsa.trackingservice.dto.request.AddTransaction;
import com.kshirsa.trackingservice.entity.enums.PaymentMode;
import com.kshirsa.trackingservice.entity.enums.TransactionType;
import com.kshirsa.userservice.entity.UserDetails;
import com.kshirsa.utility.IdGenerator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
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
    private Double amount;
    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;
    private String note;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime transactionTime;
    private Boolean isRecurring;
    @CreationTimestamp
    @Column(updatable =false)
    private Instant createdOn;
    @UpdateTimestamp
    private Instant updatedOn;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @OneToOne(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true)
    private LoanDetails loanDetails;
    @ElementCollection()
    @CollectionTable(name = "transaction_tags", joinColumns = @JoinColumn(name = "transaction_id"))
    private Set<String> tags;
    @ManyToOne(cascade = CascadeType.REMOVE)
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

