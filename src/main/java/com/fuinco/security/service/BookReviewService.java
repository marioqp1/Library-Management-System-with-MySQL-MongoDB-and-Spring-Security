package com.fuinco.security.service;

import com.fuinco.security.dto.ApiResponse;
import com.fuinco.security.entity.BookReview;
import com.fuinco.security.repository.BookReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookReviewService {

    private final BookReviewRepository bookReviewRepository;

    public BookReviewService(BookReviewRepository bookReviewRepository) {
        this.bookReviewRepository = bookReviewRepository;
    }

    @Transactional
    public ApiResponse<BookReview> createReview(BookReview bookReview) {
        ApiResponse<BookReview> response = new ApiResponse<>();
        if(bookReview.getReviewDate()== null){
            bookReview.setReviewDate(new Date());
        }

        try {
            BookReview savedReview = bookReviewRepository.save(bookReview);
            response.setEntity(savedReview);
            response.setStatusCode(201); // HTTP 201 Created
            response.setMessage("Review successfully created");
            response.setSuccess(true);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Failed to create review: " + e.getMessage());
            response.setSuccess(false);
        }

        return response;
    }

    @Transactional
    public ApiResponse<BookReview> updateReview(String reviewId, int bookId, BookReview updatedReview) {
        ApiResponse<BookReview> response = new ApiResponse<>();

        Optional<BookReview> existingReviewOpt = bookReviewRepository.findById(reviewId);

        if (existingReviewOpt.isPresent()) {
            BookReview existingReview = existingReviewOpt.get();
            existingReview.setRating(updatedReview.getRating());
            existingReview.setReviewText(updatedReview.getReviewText());
            existingReview.setReviewDate(updatedReview.getReviewDate());
            existingReview.setBookId(bookId);

            BookReview savedReview = bookReviewRepository.save(existingReview);

            response.setEntity(savedReview);
            response.setStatusCode(200);
            response.setMessage("Review successfully updated");
            response.setSuccess(true);
        } else {
            response.setStatusCode(404);
            response.setMessage("Review not found");
            response.setSuccess(false);
        }

        return response;
    }

    @Transactional
    public ApiResponse<Void> deleteReview(String reviewId) {
        ApiResponse<Void> response = new ApiResponse<>();

        try {
            if (bookReviewRepository.existsById(reviewId)) {
                bookReviewRepository.deleteById(reviewId);
                response.setStatusCode(200);
                response.setMessage("Review successfully deleted");
                response.setSuccess(true);
            } else {
                response.setStatusCode(404);
                response.setMessage("Review not found");
                response.setSuccess(false);
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Failed to delete review: " + e.getMessage());
            response.setSuccess(false);
        }

        return response;
    }

    public ApiResponse<List<BookReview>> getAllReviews() {
        ApiResponse<List<BookReview>> response = new ApiResponse<>();

        try {
            List<BookReview> reviews = bookReviewRepository.findAll();
            response.setEntity(reviews);
            response.setStatusCode(200);
            response.setMessage("Successfully retrieved all reviews");
            response.setSuccess(true);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Failed to retrieve reviews: " + e.getMessage());
            response.setSuccess(false);
        }

        return response;
    }

    public ApiResponse<List<BookReview>> getReviewsByBookIsbn(int bookId) {
        ApiResponse<List<BookReview>> response = new ApiResponse<>();

        try {
            List<BookReview> reviews = bookReviewRepository.findReviewsByBookId(bookId);
            response.setEntity(reviews);
            response.setStatusCode(200);
            response.setMessage("Successfully retrieved reviews for book reviews");
            response.setSuccess(true);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Failed to retrieve reviews: " + e.getMessage());
            response.setSuccess(false);
        }

        return response;
    }
}
