package com.example.MovieReviewSystem.Service;

import com.example.MovieReviewSystem.Entity.Comment;
import com.example.MovieReviewSystem.Entity.Review;
import com.example.MovieReviewSystem.Entity.User;
import com.example.MovieReviewSystem.Repository.CommentRepo;
import com.example.MovieReviewSystem.Repository.ReviewRepo;
import com.example.MovieReviewSystem.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CommentService {

    @Autowired
    private CommentRepo commentRepository;

    @Autowired
    private ReviewRepo reviewRepository;

    @Autowired
    private UserRepo userRepository;

    public Comment addComment(Long reviewId, String username, String content, Long parentId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NoSuchElementException("Review not found"));

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Comment comment = new Comment();
        comment.setReview(review);
        comment.setUser(user);
        comment.setContent(content);

        if (parentId != null) {
            Comment parent = commentRepository.findById(parentId)
                    .orElseThrow(() -> new NoSuchElementException("Parent comment not found"));
            comment.setParent(parent);
        }

        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByReviewId(Long reviewId) {
        return commentRepository.findByReviewId(reviewId);
    }
}
