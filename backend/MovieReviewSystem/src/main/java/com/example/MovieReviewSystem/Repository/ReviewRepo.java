package com.example.MovieReviewSystem.Repository;

import com.example.MovieReviewSystem.Entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepo extends JpaRepository<Review, Long> {
    List<Review> findByMovieId(Long movieId);

    List<Review> findByUserId(Long userId);
}
