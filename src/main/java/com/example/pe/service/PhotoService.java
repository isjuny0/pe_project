package com.example.pe.service;

import com.example.pe.dto.request.PhotoUploadRequestDto;
import com.example.pe.dto.response.PhotoResponseDto;

import java.util.List;

public interface PhotoService {
    PhotoResponseDto uploadPhoto(PhotoUploadRequestDto request, String userEmail);
    List<PhotoResponseDto> getAllPhotos();
    PhotoResponseDto getPhotoById(Long photoId, String userEmail);
    List<PhotoResponseDto> getMyPhotos(String userEmail);
}
