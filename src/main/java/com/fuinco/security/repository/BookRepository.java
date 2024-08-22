package com.fuinco.security.repository;

import com.fuinco.security.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query(value = "SELECT * FROM books WHERE id = :id", nativeQuery = true)
    Optional<Book> findBookById(@Param("id") int id);

    @Query(value = "SELECT * FROM books WHERE author LIKE %:author%", nativeQuery = true)
    List<Book> findByAuthorContaining(@Param("author") String author);

    @Query(value = "SELECT * FROM books WHERE title LIKE %:title%", nativeQuery = true)
    List<Book> findByTitleContaining(@Param("title") String title);

    @Query(value = "SELECT * FROM books WHERE genre = :genre", nativeQuery = true)
    List<Book> findByGenre(@Param("genre") String genre);

    @Query(value = "SELECT * FROM books WHERE isbn = :isbn", nativeQuery = true)
    Optional<Book> findBookByIsbn(@Param("isbn") String isbn);

}
