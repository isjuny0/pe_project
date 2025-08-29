package com.example.pe.controller;

import com.example.pe.dto.request.GalleryUpdateRequestDto;
import com.example.pe.dto.response.GalleryInfoResponseDto;
import com.example.pe.dto.response.GalleryListItemDto;
import com.example.pe.dto.response.UserResponseDto;
import com.example.pe.security.CustomUserDetails;
import com.example.pe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMyInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        UserResponseDto response = userService.getUserInfo(userDetails.getUsername());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/gallery")
    public ResponseEntity<GalleryInfoResponseDto> getUserGallery(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserGallery(userId));
    }

    @PutMapping("/me/gallery")
    public ResponseEntity<Void> updateUserGallery(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody GalleryUpdateRequestDto request
    ){
        userService.updateUserGallery(userDetails.getUsername(), request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/galleries")
    public ResponseEntity<List<GalleryListItemDto>> getAllGalleries() {
        return ResponseEntity.ok(userService.getAllGalleries());
    }
}
