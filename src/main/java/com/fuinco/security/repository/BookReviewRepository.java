package com.fuinco.security.repository;

import com.fuinco.security.entity.BookReview;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookReviewRepository extends MongoRepository<BookReview, String> {

    // Find all reviews
    @Query("{}")
    List<BookReview> findAllReviews();

    // Find review by ID
    @Query("{ '_id': ?0 }")
    BookReview findReviewById(String id);

    // Find reviews by book ID
    @Query("{ 'bookId': ?0 }")
    List<BookReview> findReviewsByBookId(int bookId);

    // Delete review by ID
    @Query(value = "{ '_id': ?0 }", delete = true)
    void deleteReviewById(String id);
}
