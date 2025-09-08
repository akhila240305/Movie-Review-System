package com.example.MovieReviewSystem.Controller;

import com.example.MovieReviewSystem.Entity.Comment;
import com.example.MovieReviewSystem.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/review/{reviewId}")
    public ResponseEntity<Comment> addComment(
            @PathVariable Long reviewId,
            @RequestBody Map<String, Object> payload,
            Principal principal) {

        String content = (String) payload.get("content");
        Long parentId = payload.get("parentId") != null ? Long.valueOf(payload.get("parentId").toString()) : null;

        Comment comment = commentService.addComment(reviewId, principal.getName(), content, parentId);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @GetMapping("/review/{reviewId}")
    public ResponseEntity<List<Comment>> getComments(@PathVariable Long reviewId) {
        return ResponseEntity.ok(commentService.getCommentsByReviewId(reviewId));
    }
}
