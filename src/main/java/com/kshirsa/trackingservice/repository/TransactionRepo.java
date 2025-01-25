package com.kshirsa.trackingservice.repository;

import com.kshirsa.trackingservice.entity.Transactions;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepo extends ListCrudRepository<Transactions, String> {

    List<Transactions> findAllByUserDetails_UserId(String user, Pageable pageable);

    @Query(value = """
                        WITH search_results AS ( SELECT tr.* FROM transactions tr WHERE tr.user_id = ?9 )
                        SELECT DISTINCT t.* FROM search_results t
                        INNER JOIN category c ON t.category_id = c.category_id
                        INNER JOIN transaction_tags tt ON t.transaction_id = tt.transaction_id
                        WHERE ( c.category_name = ANY(CAST(?1 AS text[])) OR CAST(?1 AS text[]) IS NULL )
                        AND ( t.payment_mode = ANY(CAST(?2 AS text[])) OR CAST(?2 AS text[]) IS NULL )
                        AND ( t.transaction_type = ANY(CAST(?3 AS text[])) OR CAST(?3 AS text[]) IS NULL )
                        AND ( t.amount <= CAST(?4 AS numeric) OR CAST(?4 AS numeric) IS NULL )
                        AND ( t.amount >= CAST(?5 AS numeric) OR CAST(?5 AS numeric) IS NULL )
                        AND ( t.transaction_time >= CAST(?6 AS timestamp) OR CAST(?6 AS timestamp) IS NULL )
                        AND ( t.transaction_time <= CAST(?7 AS timestamp) OR CAST(?7 AS timestamp) IS NULL )
                        AND ( tt.tags ILIKE ANY(CAST(?8 AS text[])) OR CAST(?8 AS text[]) IS NULL )
            """, nativeQuery = true)
    List<Transactions> findTransactions(String[] category, String[] paymentMode, String[] transactionType,
                                        Double amountMax, Double amountMin, LocalDateTime transactionAfter,
                                        LocalDateTime transactionBefore, String[] hashtags, String user, PageRequest pageRequest);
}
