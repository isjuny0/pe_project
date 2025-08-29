package com.example.pe.controller;

import com.example.pe.dto.request.PhotoUploadRequestDto;
import com.example.pe.dto.response.PhotoResponseDto;
import com.example.pe.service.PhotoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/photos")
public class PhotoController {

    private final PhotoService photoService;

    @PostMapping
    public ResponseEntity<PhotoResponseDto> uploadPhoto(
            @RequestPart("data") String dataJson,
            @RequestPart("photo") MultipartFile photo,
            @AuthenticationPrincipal UserDetails userDetails) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        PhotoUploadRequestDto request = objectMapper.readValue(dataJson, PhotoUploadRequestDto.class);

        request.setPhoto(photo);
        PhotoResponseDto response = photoService.uploadPhoto(request, userDetails.getUsername());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PhotoResponseDto>> getAllPhotos() {
        return ResponseEntity.ok(photoService.getAllPhotos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhotoResponseDto> getPhotoById(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(photoService.getPhotoById(id, userDetails.getUsername()));
    }

    @GetMapping("/me")
    public ResponseEntity<List<PhotoResponseDto>> getUserPhotos(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(photoService.getMyPhotos(userDetails.getUsername()));
    }

}
