package com.example.MovieReviewSystem.Controller;


import com.example.MovieReviewSystem.Entity.Review;
import com.example.MovieReviewSystem.Entity.User;
import com.example.MovieReviewSystem.Security.JwtUtil;
import com.example.MovieReviewSystem.Security.UserDetailsServiceImpl;
import com.example.MovieReviewSystem.Service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired private ReviewService reviewService;
    @Autowired
    private UserDetailsServiceImpl userService;

    @PostMapping("/movie/{movieId}")
    public ResponseEntity<Review> addReview(
            @PathVariable Long movieId,
            @RequestBody Review review,
            Principal principal) {

        String email = principal.getName(); // Extract user email from JWT
        return ResponseEntity.ok(reviewService.addReview(movieId, email, review));
    }



    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Review>> getReviewsByMovie(@PathVariable Long movieId) {
        return ResponseEntity.ok(reviewService.getReviewsByMovie(movieId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Review review) {
        return ResponseEntity.ok(reviewService.updateReview(id, review));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok("Review Deleted");
    }
}
