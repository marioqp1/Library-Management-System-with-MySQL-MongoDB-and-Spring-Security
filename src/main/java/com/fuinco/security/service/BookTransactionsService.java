package com.fuinco.security.service;


import com.fuinco.security.dto.ApiResponse;
import com.fuinco.security.entity.BookTransactions;
import com.fuinco.security.repository.BookRepository;
import com.fuinco.security.repository.BookTransactionRepository;
import com.fuinco.security.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookTransactionsService {
    private final BookTransactionRepository bookTransactionRepository;
    private final BookRepository bookRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public BookTransactionsService(BookTransactionRepository bookTransactionRepository, BookRepository bookRepository, UserService userService, UserRepository userRepository) {
        this.bookTransactionRepository = bookTransactionRepository;
        this.bookRepository = bookRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Transactional
    public ApiResponse<BookTransactions> createBookTransaction(BookTransactions bookTransaction) {
        ApiResponse<BookTransactions> response = new ApiResponse<>();
        try {
            // Set the transaction date to the current date
            bookTransaction.setTransactionDate(new Date());
            BookTransactions savedTransaction = bookTransactionRepository.save(bookTransaction);
            response.setEntity(savedTransaction);
            response.setStatusCode(200);
            response.setMessage("Book transaction successfully created");
            response.setSuccess(true);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Failed to create book transaction: " + e.getMessage());
            response.setSuccess(false);
        }
        return response;
    }


    public ApiResponse<BookTransactions> updateBookTransaction(BookTransactions bookTransaction, int bookTransactionId) {
        ApiResponse<BookTransactions> response = new ApiResponse<>();
        if (bookTransaction != null) {
            bookTransaction.setId(bookTransactionId);
            BookTransactions updatedBookTransaction = bookTransactionRepository.save(bookTransaction);
            response.setEntity(updatedBookTransaction);
            response.setStatusCode(200);
            response.setMessage("Book transaction successfully updated");
            response.setSuccess(true);
            return response;
        }
        response.setStatusCode(400);
        response.setMessage("Book transaction could not be updated");
        response.setSuccess(false);
        return response;

    }

    public ApiResponse<BookTransactions> deleteBookTransaction(int bookTransactionId) {
        ApiResponse<BookTransactions> response = new ApiResponse<>();
        if (bookTransactionId > 0) {
            if (bookTransactionRepository.existsById(bookTransactionId)) {
                bookTransactionRepository.deleteById(bookTransactionId);
                response.setStatusCode(200);
                response.setMessage("Book transaction successfully deleted");
                response.setSuccess(true);
                return response;
            }
            response.setMessage("Book transaction cannot be found");
            response.setStatusCode(404);
            response.setSuccess(false);
            return response;
        }
        response.setStatusCode(400);
        response.setMessage("Book transaction could not be deleted");
        response.setSuccess(false);
        return response;

    }

    public ApiResponse<BookTransactions>getBookTransaction(int bookTransactionId) {
        ApiResponse<BookTransactions> response = new ApiResponse<>();
        if (bookTransactionId > 0) {
            if (bookTransactionRepository.existsById(bookTransactionId)) {
                BookTransactions bookTransaction = bookTransactionRepository.findById(bookTransactionId).get();
                response.setEntity(bookTransaction);
                response.setStatusCode(200);
                response.setMessage("Book transaction successfully retrieved");
                response.setSuccess(true);
                return response;
            }
            response.setStatusCode(404);
            response.setMessage("Book transaction cannot be found");
            response.setSuccess(false);
            return response;
        }
        response.setStatusCode(400);
        response.setMessage("Book transaction could not be retrieved : id is not correct");
        response.setSuccess(false);
        return response;
    }

    public ApiResponse<List<BookTransactions>> getAllBookTransactions( ) {
        ApiResponse<List<BookTransactions>>response = new ApiResponse<>();
        response.setStatusCode(200);
        List<BookTransactions> list = bookTransactionRepository.findAll();
        response.setMessage("Book transactions successfully retrieved");
        response.setSuccess(true);
        response.setEntity(list);
        return response;
    }
    public ApiResponse<BookTransactions> getBookTransactionByBookAndUser(int bookId, int userId) {
        ApiResponse<BookTransactions> response = new ApiResponse<>();
        if (!bookRepository.existsById(bookId)){
            response.setStatusCode(404);
            response.setMessage("Book cannot be found");
            response.setSuccess(false);
            return response;
        }
        if(!userRepository.existsById(userId)){
            response.setStatusCode(404);
            response.setMessage("User cannot be found");
            response.setSuccess(false);
            return response;
        }
        Optional<BookTransactions> bookTransaction = bookTransactionRepository.findByBookIdAndUserId(bookId,userId);
        if (bookTransaction.isEmpty()){
            response.setStatusCode(404);
            response.setMessage("Book transaction cannot be found");
            response.setSuccess(false);
            return response;
        }
        response.setStatusCode(200);
        response.setMessage("Book transaction successfully retrieved");
        response.setSuccess(true);
        response.setEntity(bookTransaction.get());
        return response;
    }

}
