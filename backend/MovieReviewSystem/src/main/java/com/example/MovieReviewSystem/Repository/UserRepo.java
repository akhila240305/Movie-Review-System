package com.example.MovieReviewSystem.Repository;

import com.example.MovieReviewSystem.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    // Admin queries
    List<User> findByRole(String role);

    @Query("SELECT COUNT(u) FROM User u WHERE u.role = :role")
    Long countByRole(@Param("role") String role);

    @Query("SELECT u FROM User u ORDER BY u.id DESC")
    List<User> findAllOrderByIdDesc();

    // Find users by name containing (for search)
    List<User> findByNameContainingIgnoreCase(String name);

    // Find users by email containing (for search)
    List<User> findByEmailContainingIgnoreCase(String email);
}