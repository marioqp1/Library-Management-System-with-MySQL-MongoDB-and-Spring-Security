package com.fuinco.security.controller;

import com.fuinco.security.dto.ApiResponse;
import com.fuinco.security.dto.BookDto;
import com.fuinco.security.entity.Book;
import com.fuinco.security.entity.BookReview;
import com.fuinco.security.entity.BookTransactions;
import com.fuinco.security.repository.BookRepository;
import com.fuinco.security.service.BookReviewService;
import com.fuinco.security.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    private final BookRepository bookRepository;
    private final BookReviewService bookReviewService;

    public BookController(BookService bookService, BookRepository bookRepository, BookReviewService bookReviewService) {
        this.bookService = bookService;
        this.bookRepository = bookRepository;
        this.bookReviewService = bookReviewService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> getBookById(@PathVariable int id) {
        ApiResponse<Book> response = bookService.getBookById(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @GetMapping("/get")
    public ApiResponse<List<Book>> getAllBooks() {

        return bookService.getAllBooks();
    }

    @PostMapping("/add")

    public ResponseEntity<ApiResponse<Book>> addBook( @RequestBody BookDto bookDto) {
        ApiResponse<Book> response = bookService.addBook(bookDto);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> updateBook(@PathVariable int id, @RequestBody BookDto bookDto) {
        ApiResponse<Book> response = bookService.updateBook(id, bookDto);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> deleteBook(@PathVariable int id) {
        ApiResponse<Book> response = bookService.deleteBook(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @GetMapping("/search/author")
    public ResponseEntity<ApiResponse<List<Book>>> searchBooksByAuthor(@RequestParam String keyword) {
        ApiResponse<List<Book>> response = bookService.searchBooksByAuthor(keyword);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @GetMapping("/search/title")
    public ResponseEntity<ApiResponse<List<Book>>> searchBooksByTitle(@RequestParam String keyword) {
        ApiResponse<List<Book>> response = bookService.searchBooksByTitle(keyword);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @GetMapping("/search/genres")
    public ResponseEntity<ApiResponse<List<Book>>> searchBooksByGenres(@RequestParam String keyword) {
        ApiResponse<List<Book>> response = bookService.searchBooksByGenres(keyword);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @PostMapping("/{isbn}/borrow")
    public ResponseEntity<ApiResponse<BookTransactions>> borrowBook(@PathVariable String isbn, @RequestParam int userId) {
        ApiResponse<BookTransactions> response = bookService.borrowBook(isbn, userId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @PostMapping("/{isbn}/return")
    public ResponseEntity<ApiResponse<BookTransactions>> returnBook(@PathVariable String isbn, @RequestParam int userId) {
        ApiResponse<BookTransactions> response = bookService.returnBook(isbn, userId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));

    }
    @GetMapping("/{isbn}/reviews")
    public ResponseEntity<ApiResponse<List<BookReview>>> getReviewsByBookIsbn(@PathVariable String isbn) {

        Optional<Book> book = bookRepository.findBookByIsbn(isbn);
        if (book.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ApiResponse<List<BookReview>> response = bookReviewService.getReviewsByBookIsbn(book.get().getId());
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
}
