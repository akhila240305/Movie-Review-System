package com.example.MovieReviewSystem.Repository;

import com.example.MovieReviewSystem.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
    List<Comment> findByReviewId(Long reviewId);
}
