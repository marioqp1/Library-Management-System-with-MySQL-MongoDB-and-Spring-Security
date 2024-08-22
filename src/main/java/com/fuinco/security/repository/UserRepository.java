package com.fuinco.security.repository;

import com.fuinco.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT * FROM users WHERE username = :username", nativeQuery = true)
    User findByUsername(String username);

    @Query(value = "DELETE from users WHERE id =:id", nativeQuery = true)
    void deleteByUserId(int id);

    @Query(value = "SELECT * FROM users WHERE id =:id", nativeQuery = true)
    Optional<User> findById(int id);

    boolean existsByUsername(String username);

    //    @Query(value = "INSERT INTO users (username, password, enabled) VALUES (:username, :password, :enabled)", nativeQuery = true)
//    void saveUser(@Param("username") String username,
//                  @Param("password") String password,
//                  @Param("enabled") boolean enabled);
//    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM user WHERE username = :username", nativeQuery = true)
//    boolean existsByUsername(@Param("username") String username);
}


