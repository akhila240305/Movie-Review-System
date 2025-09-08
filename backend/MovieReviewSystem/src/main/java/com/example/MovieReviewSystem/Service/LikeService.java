package com.example.MovieReviewSystem.Service;

import com.example.MovieReviewSystem.Entity.Like;
import com.example.MovieReviewSystem.Entity.Review;
import com.example.MovieReviewSystem.Entity.User;
import com.example.MovieReviewSystem.Repository.LikeRepo;
import com.example.MovieReviewSystem.Repository.ReviewRepo;
import com.example.MovieReviewSystem.Repository.UserRepo;
import com.example.MovieReviewSystem.Security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeService {

    @Autowired
    private LikeRepo likeRepository;

    @Autowired
    private ReviewRepo reviewRepository;

    @Autowired
    private JwtUtil jwtService;

    @Autowired
    private UserRepo userRepository;

    public void likeReview(Long reviewId, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String email = jwtService.extractUsername(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (likeRepository.findByReviewIdAndUserId(reviewId, user.getId()).isPresent()) {
            throw new RuntimeException("Already liked this review");
        }

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        Like like = new Like();
        like.setReview(review);
        like.setUser(user);
        likeRepository.save(like);
    }

    @Transactional
    public void unlikeReview(Long reviewId, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String email = jwtService.extractUsername(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        likeRepository.deleteByReviewIdAndUserId(reviewId, user.getId());
    }

    public long countLikes(Long reviewId) {
        return likeRepository.countByReviewId(reviewId);
    }
}
