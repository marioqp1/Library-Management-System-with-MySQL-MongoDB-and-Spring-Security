package com.fuinco.security.repository;

import com.fuinco.security.entity.BookTransactions;
import com.fuinco.security.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookTransactionRepository extends JpaRepository<BookTransactions, Integer> {

    @Query(value = "SELECT * FROM book_transactions WHERE book_id = :bookId AND user_id = :userId", nativeQuery = true)
    Optional<BookTransactions> findByBookIdAndUserId(@Param("bookId") int bookId, @Param("userId") int userId);

    @Query("SELECT COUNT(bt) FROM BookTransactions bt WHERE bt.book.id = :bookId AND bt.user.id = :userId AND bt.transactionType = :transactionType")
    long countByBookIdAndUserIdAndTransactionType(int bookId, int userId, TransactionType transactionType);
}
