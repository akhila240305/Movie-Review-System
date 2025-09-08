package com.example.MovieReviewSystem.Service;

import com.example.MovieReviewSystem.Entity.Movie;
import com.example.MovieReviewSystem.Entity.Review;
import com.example.MovieReviewSystem.Repository.MovieRepo;
import com.example.MovieReviewSystem.Repository.ReviewRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private MovieRepo movieRepository;
    @Autowired
    private ReviewRepo reviewRepo;

    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie updateMovie(Long id, Movie updatedMovie) {
        Movie movie = movieRepository.findById(id).orElseThrow();
        movie.setTitle(updatedMovie.getTitle());
        movie.setGenre(updatedMovie.getGenre());
        movie.setDescription(updatedMovie.getDescription());
        movie.setReleaseDate(updatedMovie.getReleaseDate());
        movie.setImageUrl(updatedMovie.getImageUrl());
        return movieRepository.save(movie);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id).orElseThrow();
    }

    public List<Movie> getTopRatedMovies(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return movieRepository.findTopRatedMovies(pageable);
    }

}
