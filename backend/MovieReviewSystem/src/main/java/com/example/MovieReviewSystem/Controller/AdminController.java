package com.example.MovieReviewSystem.Controller;

import com.example.MovieReviewSystem.Entity.Movie;
import com.example.MovieReviewSystem.Entity.User;
import com.example.MovieReviewSystem.Service.MovieService;
import com.example.MovieReviewSystem.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

        import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;

    // Get all users for admin management
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Update user role
    @PutMapping("/users/{id}/role")
    public ResponseEntity<User> updateUserRole(@PathVariable Long id, @RequestParam String role) {
        try {
            User updatedUser = userService.updateUserRole(id, role);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            if (e instanceof IllegalArgumentException) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.notFound().build();
        }
    }

    // Delete user
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Search users
    @GetMapping("/users/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String query) {
        return ResponseEntity.ok(userService.searchUsers(query));
    }

    // Get user statistics for dashboard
    @GetMapping("/stats/users")
    public ResponseEntity<Map<String, Long>> getUserStatistics() {
        return ResponseEntity.ok(userService.getUserStatistics());
    }

    // Get users by role
    @GetMapping("/users/role/{role}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable String role) {
        return ResponseEntity.ok(userService.getUsersByRole(role));
    }

    @Autowired
    private MovieService movieService;

    // Add Movie
    @PostMapping("/movies")
    public ResponseEntity<?> addMovie(@RequestBody Movie movie) {
        try {
            return ResponseEntity.ok(movieService.addMovie(movie));
        } catch (Exception e) {
            e.printStackTrace(); // 🔍 this will show the root error in console
            return ResponseEntity.status(500).body("Error adding movie: " + e.getMessage());
        }
    }

    // Update Movie
    @PutMapping("/movies/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
        return ResponseEntity.ok(movieService.updateMovie(id, movie));
    }

    // Delete Movie
    @DeleteMapping("/movies/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok("Movie deleted successfully");
    }

    // Get All Movies
    @GetMapping("/movies")
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

}