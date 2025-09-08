package com.example.MovieReviewSystem.Service;


import com.example.MovieReviewSystem.Entity.Movie;
import com.example.MovieReviewSystem.Entity.Review;
import com.example.MovieReviewSystem.Entity.User;
import com.example.MovieReviewSystem.Repository.MovieRepo;
import com.example.MovieReviewSystem.Repository.ReviewRepo;
import com.example.MovieReviewSystem.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReviewService {

    @Autowired private ReviewRepo reviewRepository;
    @Autowired private UserRepo userRepository;
    @Autowired private MovieRepo movieRepository;

    public Review addReview(Long movieId, String email, Review review) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        review.setUser(user);
        review.setMovie(movie);
        review.setTimestamp(LocalDateTime.now());

        return reviewRepository.save(review);
    }


    public List<Review> getReviewsByMovie(Long movieId) {
        return reviewRepository.findByMovieId(movieId);
    }
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review updateReview(Long reviewId, Review updatedReview) {
        Review review = reviewRepository.findById(reviewId).orElseThrow();
        review.setContent(updatedReview.getContent());
        review.setRating(updatedReview.getRating());
        return reviewRepository.save(review);
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
