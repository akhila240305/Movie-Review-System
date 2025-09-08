package com.example.MovieReviewSystem.Repository;

import com.example.MovieReviewSystem.Entity.Like;
import com.example.MovieReviewSystem.Entity.Movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeRepo extends JpaRepository<Like, Long> {
    Optional<Like> findByReviewIdAndUserId(Long reviewId, Long userId);

    void deleteByReviewIdAndUserId(Long reviewId, Long userId);

    long countByReviewId(Long reviewId);

}
