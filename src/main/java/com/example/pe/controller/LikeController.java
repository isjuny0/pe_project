package com.example.pe.controller;

import com.example.pe.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/photos/{photoId}/likes")
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> toggleLike(
            @PathVariable Long photoId,
            @AuthenticationPrincipal UserDetails userDetails) {
        boolean liked = likeService.toggleLike(photoId, userDetails.getUsername());
        int likeCount = likeService.getLikeCount(photoId);

        return ResponseEntity.ok(Map.of(
                "liked", liked,
                "count", likeCount
        ));
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Integer>> getLikeCount(@PathVariable Long photoId) {
        int likeCount = likeService.getLikeCount(photoId);
        return ResponseEntity.ok(Map.of("likeCount", likeCount));
    }
}
