package com.fuinco.security.service;

import com.fuinco.security.dto.ApiResponse;
import com.fuinco.security.dto.BookDto;
import com.fuinco.security.entity.*;
import com.fuinco.security.repository.BookRepository;
import com.fuinco.security.repository.BookTransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final GenresService genresService;
    private final PublisherService publisherService;
    private final BookTransactionsService bookTransactionsService;
    private final UserService userService;
    private final BookTransactionRepository bookTransactionRepository;

    public BookService(BookRepository bookRepository, GenresService genresService, PublisherService publisherService, BookTransactionsService bookTransactionsService, UserService userService, BookTransactionRepository bookTransactionRepository) {
        this.bookRepository = bookRepository;
        this.genresService = genresService;
        this.publisherService = publisherService;
        this.bookTransactionsService = bookTransactionsService;
        this.userService = userService;
        this.bookTransactionRepository = bookTransactionRepository;
    }

    public ApiResponse<Book> getBookById(int id) {
        ApiResponse<Book> response = new ApiResponse<>();
        Optional<Book> book = bookRepository.findById(id);

        if (book.isPresent()) {
            Publisher publisher = book.get().getPublisher();
            publisher.setBooks(new ArrayList<>());
            book.get().setPublisher(publisher);
            response.setEntity(book.get());
            response.setMessage("Found book with id " + id);
            response.setSuccess(true);
            response.setStatusCode(200);
        } else {
            response.setMessage("Book not found with id " + id);
            response.setSuccess(false);
            response.setStatusCode(404);
        }
        return response;
    }

    public ApiResponse<List<Book>> getAllBooks() {
        ApiResponse<List<Book>> response = new ApiResponse<>();
        List<Book> books = bookRepository.findAll();
        for (Book book : books) {
            Publisher publisher = book.getPublisher();
            publisher.setBooks(new ArrayList<>());
            book.setPublisher(publisher);
        }
        response.setEntity(books);
        response.setMessage("Found " + books.size() + " books");
        response.setSuccess(true);
        response.setStatusCode(200);
        return response;
    }

    @Transactional
    public ApiResponse<Book> addBook(BookDto bookDto) {
        ApiResponse<Book> response = new ApiResponse<>();
        Book book = new Book();

        book.setIsbn(bookDto.getIsbn());
        book.setTitle(bookDto.getTitle());
        book.setDescription(bookDto.getDescription());
        book.setCopiesAvailable(bookDto.getCopiesAvailable());
        book.setPublishDate(bookDto.getPublishDate());

        // Fetch and set genres
        Genres genre = genresService.getGenre(bookDto.getGenresId()).getEntity();
        if (genre == null) {
            response.setStatusCode(404);
            response.setMessage("Genre not found");
            response.setSuccess(false);
            return response;
        }
        book.setGenres(genre);

        // Fetch and set publisher
        Publisher publisher = publisherService.getPublisher(bookDto.getPublisherID()).getEntity();
        if (publisher == null) {
            response.setStatusCode(404);
            response.setMessage("Publisher not found");
            response.setSuccess(false);
            return response;
        }
        book.setPublisher(publisher);

        // Save the book entity
        bookRepository.save(book);
        response.setEntity(book);
        response.setMessage("Book added successfully");
        response.setSuccess(true);
        response.setStatusCode(200);

        return response;
    }

    @Transactional
    public ApiResponse<Book> updateBook(int id, BookDto bookDto) {
        ApiResponse<Book> response = new ApiResponse<>();
        Optional<Book> bookOptional = bookRepository.findById(id);

        if (bookOptional.isEmpty()) {
            response.setStatusCode(404);
            response.setMessage("Book not found");
            response.setSuccess(false);
            return response;
        }

        Book bookToUpdate = bookOptional.get();

        if (bookDto.getIsbn() != null && !bookDto.getIsbn().isEmpty()) {
            bookToUpdate.setIsbn(bookDto.getIsbn());
        }
        if (bookDto.getTitle() != null && !bookDto.getTitle().isEmpty()) {
            bookToUpdate.setTitle(bookDto.getTitle());
        }
        if (bookDto.getDescription() != null) {
            bookToUpdate.setDescription(bookDto.getDescription());
        }
        if (bookDto.getPublishDate() != null) {
            bookToUpdate.setPublishDate(bookDto.getPublishDate());
        }
        if (bookDto.getCopiesAvailable() > 0) {
            bookToUpdate.setCopiesAvailable(bookDto.getCopiesAvailable());
        }


        Optional<Genres> genresOptional = Optional.ofNullable(genresService.getGenre(bookDto.getGenresId()).getEntity());
        if (genresOptional.isEmpty()) {
            response.setStatusCode(404);
            response.setMessage("Genre not found");
            response.setSuccess(false);
            return response;
        }
        bookToUpdate.setGenres(genresOptional.get());


        Optional<Publisher> publisherOptional = Optional.ofNullable(publisherService.getPublisher(bookDto.getPublisherID()).getEntity());
        if (publisherOptional.isEmpty()) {
            response.setStatusCode(404);
            response.setMessage("Publisher not found");
            response.setSuccess(false);
            return response;
        }
        bookToUpdate.setPublisher(publisherOptional.get());

        bookRepository.save(bookToUpdate);
        response.setMessage("Book updated successfully");
        response.setSuccess(true);
        response.setStatusCode(200);
        response.setEntity(bookToUpdate);
        return response;
    }

    public ApiResponse<Book> deleteBook(int id) {
        ApiResponse<Book> response = new ApiResponse<>();
        Optional<Book> bookOptional = bookRepository.findById(id);

        if (bookOptional.isPresent()) {
            bookRepository.deleteById(id);
            response.setEntity(bookOptional.get());
            response.setMessage("Book deleted");
            response.setSuccess(true);
            response.setStatusCode(200);
        } else {
            response.setMessage("Book not found with id " + id);
            response.setSuccess(false);
            response.setStatusCode(404);
        }
        return response;
    }

    public ApiResponse<List<Book>> searchBooksByAuthor(String keyword) {
        ApiResponse<List<Book>> response = new ApiResponse<>();
        List<Book> books = bookRepository.findByAuthorContaining(keyword);
        response.setEntity(books);
        response.setMessage("Found " + books.size() + " books by author containing '" + keyword + "'");
        response.setSuccess(true);
        response.setStatusCode(200);
        return response;
    }

    public ApiResponse<List<Book>> searchBooksByTitle(String keyword) {
        ApiResponse<List<Book>> response = new ApiResponse<>();
        List<Book> books = bookRepository.findByTitleContaining(keyword);
        response.setEntity(books);
        response.setMessage("Found " + books.size() + " books by title containing '" + keyword + "'");
        response.setSuccess(true);
        response.setStatusCode(200);
        return response;
    }

    public ApiResponse<List<Book>> searchBooksByGenres(String keyword) {
        ApiResponse<List<Book>> response = new ApiResponse<>();
        List<Book> books = bookRepository.findByGenre(keyword);
        response.setEntity(books);
        response.setMessage("Found " + books.size() + " books by genre containing '" + keyword + "'");
        response.setSuccess(true);
        response.setStatusCode(200);
        return response;
    }
    @Transactional
    public ApiResponse<BookTransactions> borrowBook(String isbn, int userId) {
        ApiResponse<BookTransactions> response = new ApiResponse<>();

        Optional<Book> bookOptional = bookRepository.findBookByIsbn(isbn);
        Optional<User> userOptional = Optional.ofNullable(userService.findUserById(userId).getEntity());

        if (bookOptional.isEmpty()) {
            response.setStatusCode(404);
            response.setMessage("Book not found");
            response.setSuccess(false);
            return response;
        }
        if (userOptional.isEmpty()) {
            response.setStatusCode(404);
            response.setMessage("User not found");
            response.setSuccess(false);
            return response;
        }

        Book book = bookOptional.get();
        User user = userOptional.get();
        long returnTimes = bookTransactionRepository.countByBookIdAndUserIdAndTransactionType(book.getId(), userId, TransactionType.RETURN);
        long borrowTimes = bookTransactionRepository.countByBookIdAndUserIdAndTransactionType(book.getId(), userId, TransactionType.BORROW);
        if (borrowTimes > returnTimes) {
            response.setStatusCode(400);
            response.setMessage("The user need to return the book first");
            response.setSuccess(false);
            return response;
        }

        int currentBookCopiesAvailable = book.getCopiesAvailable();
        if (currentBookCopiesAvailable > 0) {
            book.setCopiesAvailable(currentBookCopiesAvailable - 1);
            bookRepository.save(book);

            BookTransactions bookTransactions = new BookTransactions();
            bookTransactions.setBook(book);
            bookTransactions.setTransactionType(TransactionType.BORROW);
            bookTransactions.setUser(user);

            response = bookTransactionsService.createBookTransaction(bookTransactions);
            return response;

        } else {
            response.setStatusCode(400);
            response.setMessage("No copies of the book available");
            response.setSuccess(false);
            return response;
        }
    }

    @Transactional
    public ApiResponse<BookTransactions> returnBook(String isbn, int userId) {
        ApiResponse<BookTransactions> response = new ApiResponse<>();

        Optional<Book> bookOptional = bookRepository.findBookByIsbn(isbn);
        Optional<User> userOptional = Optional.ofNullable(userService.findUserById(userId).getEntity());

        if (bookOptional.isEmpty()) {
            response.setStatusCode(404);
            response.setMessage("Book not found");
            response.setSuccess(false);
            return response;
        }
        if (userOptional.isEmpty()) {
            response.setStatusCode(404);
            response.setMessage("User not found");
            response.setSuccess(false);
            return response;
        }

        Book book = bookOptional.get();
        User user = userOptional.get();
        Optional<BookTransactions> checkBookTransaction = Optional.ofNullable(bookTransactionsService.getBookTransactionByBookAndUser(book.getId(), userId).getEntity());
        if (checkBookTransaction.isEmpty()) {
            response.setStatusCode(404);
            response.setMessage("Book transaction not found");
            response.setSuccess(false);
            return response;
        }
        long returnTimes = bookTransactionRepository.countByBookIdAndUserIdAndTransactionType(book.getId(), userId, TransactionType.RETURN);
        long borrowTimes = bookTransactionRepository.countByBookIdAndUserIdAndTransactionType(book.getId(), userId, TransactionType.BORROW);
        if (borrowTimes <= returnTimes) {
            response.setStatusCode(400);
            response.setMessage("Their is no borrow Transaction to return");
            response.setSuccess(false);
            return response;
        }

        int currentBookCopiesAvailable = book.getCopiesAvailable();
        book.setCopiesAvailable(currentBookCopiesAvailable + 1);
        bookRepository.save(book);
        BookTransactions bookTransactions = new BookTransactions();
        bookTransactions.setBook(book);
        bookTransactions.setTransactionType(TransactionType.RETURN);
        bookTransactions.setUser(user);
        response = bookTransactionsService.createBookTransaction(bookTransactions);
        response.setMessage("Book transaction returned");
        return response;
    }






}



