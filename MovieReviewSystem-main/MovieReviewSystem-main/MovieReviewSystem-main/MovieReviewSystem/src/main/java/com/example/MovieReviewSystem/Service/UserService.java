package com.example.MovieReviewSystem.Service;

import com.example.MovieReviewSystem.Entity.User;
import com.example.MovieReviewSystem.Repository.UserRepo;
import com.example.MovieReviewSystem.Security.JwtUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    JwtUtil jwtUtil = new JwtUtil();

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public String authenticateUser(String email, String password) {
        // Load user from DB
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Validate password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        // Generate JWT Token using your JwtUtil
        return jwtUtil.generateToken(user.getEmail()); // ✅ Updated
    }
    // Add these methods to your existing UserService class

    public List<User> getAllUsers() {
        return userRepository.findAllOrderByIdDesc();
    }

    public User updateUserRole(Long userId, String newRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Validate role
        if (!newRole.equals("ADMIN") && !newRole.equals("USER") && !newRole.equals("REVIEWER")) {
            throw new IllegalArgumentException("Invalid role: " + newRole);
        }

        user.setRole(newRole);
        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(userId);
    }

    public List<User> searchUsers(String query) {
        List<User> usersByName = userRepository.findByNameContainingIgnoreCase(query);
        List<User> usersByEmail = userRepository.findByEmailContainingIgnoreCase(query);

        // Combine and remove duplicates
        Set<User> combinedUsers = new HashSet<>(usersByName);
        combinedUsers.addAll(usersByEmail);

        return new ArrayList<>(combinedUsers);
    }

    public Map<String, Long> getUserStatistics() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("totalUsers", userRepository.count());
        stats.put("adminCount", userRepository.countByRole("ADMIN"));
        stats.put("userCount", userRepository.countByRole("USER"));
        stats.put("reviewerCount", userRepository.countByRole("REVIEWER"));
        return stats;
    }

    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }
}
