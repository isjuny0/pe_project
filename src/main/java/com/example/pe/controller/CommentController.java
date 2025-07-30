package com.example.pe.controller;

import com.example.pe.dto.request.CommentRequestDto;
import com.example.pe.dto.response.CommentResponseDto;
import com.example.pe.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/photos/{photoId}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> addComment(
            @PathVariable Long photoId,
            @RequestBody CommentRequestDto request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(commentService.addComment(photoId, request, userDetails.getUsername()));
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getComments(
            @PathVariable Long photoId) {
        return ResponseEntity.ok(commentService.getComments(photoId));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetails userDetails) {
        commentService.deleteComment(commentId, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

}
