package com.example.MovieReviewSystem.Controller;

import com.example.MovieReviewSystem.Service.LikeService;
import com.example.MovieReviewSystem.Service.DislikeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private DislikeService dislikeService;

    // ---------- LIKE ----------
    @PostMapping("/{reviewId}/like")
    public ResponseEntity<String> likeReview(@PathVariable Long reviewId, HttpServletRequest request) {
        likeService.likeReview(reviewId, request);
        return ResponseEntity.ok("Review liked successfully");
    }

    @DeleteMapping("/{reviewId}/like")
    public ResponseEntity<String> unlikeReview(@PathVariable Long reviewId, HttpServletRequest request) {
        likeService.unlikeReview(reviewId, request);
        return ResponseEntity.ok("Review unliked successfully");
    }

    @GetMapping("/{reviewId}/likes")
    public ResponseEntity<Long> getLikeCount(@PathVariable Long reviewId) {
        return ResponseEntity.ok(likeService.countLikes(reviewId));
    }

    // ---------- DISLIKE ----------
    @PostMapping("/{reviewId}/dislike")
    public ResponseEntity<String> dislikeReview(@PathVariable Long reviewId, HttpServletRequest request) {
        dislikeService.dislikeReview(reviewId, request);
        return ResponseEntity.ok("Review disliked successfully");
    }

    @DeleteMapping("/{reviewId}/dislike")
    public ResponseEntity<String> removeDislike(@PathVariable Long reviewId, HttpServletRequest request) {
        dislikeService.removeDislike(reviewId, request);
        return ResponseEntity.ok("Review dislike removed successfully");
    }

    @GetMapping("/{reviewId}/dislikes")
    public ResponseEntity<Long> getDislikeCount(@PathVariable Long reviewId) {
        return ResponseEntity.ok(dislikeService.countDislikes(reviewId));
    }
}
