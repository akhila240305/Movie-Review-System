package com.example.MovieReviewSystem.Service;

import com.example.MovieReviewSystem.Entity.Review;
import com.example.MovieReviewSystem.Entity.User;
import com.example.MovieReviewSystem.Repository.ReviewRepo;
import com.example.MovieReviewSystem.Repository.UserRepo;
import com.example.MovieReviewSystem.Security.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class DislikeService {

    @Autowired
    private ReviewRepo reviewRepository;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private JwtUtil jwtUtil; // Your existing JWT utility

    // Store which users disliked each review (in-memory for now)
    private final Set<String> dislikes = new HashSet<>();

    public void dislikeReview(Long reviewId, HttpServletRequest request) {
        String token = extractToken(request);
        String email = jwtUtil.extractUsername(token);

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String key = reviewId + ":" + user.getId();
        dislikes.add(key);
    }

    public void removeDislike(Long reviewId, HttpServletRequest request) {
        String token = extractToken(request);
        String email = jwtUtil.extractUsername(token);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String key = reviewId + ":" + user.getId();
        dislikes.remove(key);
    }

    public long countDislikes(Long reviewId) {
        return dislikes.stream()
                .filter(k -> k.startsWith(reviewId + ":"))
                .count();
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        throw new RuntimeException("JWT token missing");
    }
}
