package com.example.MovieReviewSystem.Repository;

import com.example.MovieReviewSystem.Entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface MovieRepo extends JpaRepository<Movie, Long> {

    @Query("""
                SELECT m FROM Movie m
                JOIN Review r ON r.movie = m
                GROUP BY m.id
                ORDER BY AVG(r.rating) DESC
            """)
    List<Movie> findTopRatedMovies(Pageable pageable);

    List<Movie> findTop5ByGenreAndIdNotOrderByReleaseDateDesc(String genre, Long excludeMovieId);

}
