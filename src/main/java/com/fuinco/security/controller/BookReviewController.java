package com.fuinco.security.controller;

import com.fuinco.security.dto.ApiResponse;
import com.fuinco.security.entity.BookReview;
import com.fuinco.security.repository.BookRepository;
import com.fuinco.security.service.BookReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class BookReviewController {

    private final BookReviewService bookReviewService;
    //private final BookRepository bookRepository;

    public BookReviewController(BookReviewService bookReviewService, BookRepository bookRepository) {
        this.bookReviewService = bookReviewService;
//        this.bookRepository = bookRepository;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BookReview>> createReview(@RequestBody BookReview bookReview) {
        ApiResponse<BookReview> response = bookReviewService.createReview(bookReview);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<BookReview>> updateReview(@PathVariable String reviewId, @RequestParam int bookId, @RequestBody BookReview updatedReview) {
        ApiResponse<BookReview> response = bookReviewService.updateReview(reviewId, bookId, updatedReview);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(@PathVariable String reviewId) {
        ApiResponse<Void> response = bookReviewService.deleteReview(reviewId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookReview>>> getAllReviews() {
        ApiResponse<List<BookReview>> response = bookReviewService.getAllReviews();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }


}
