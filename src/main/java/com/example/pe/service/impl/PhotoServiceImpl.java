package com.example.pe.service.impl;

import com.example.pe.dto.request.PhotoUploadRequestDto;
import com.example.pe.dto.response.PhotoResponseDto;
import com.example.pe.entity.Photo;
import com.example.pe.entity.User;
import com.example.pe.repository.LikeRepository;
import com.example.pe.repository.PhotoRepository;
import com.example.pe.repository.UserRepository;
import com.example.pe.service.PhotoService;
import com.example.pe.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;
    private final LikeRepository likeRepository;

    @Override
    @Transactional
    public PhotoResponseDto uploadPhoto(PhotoUploadRequestDto request, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String photoUrl = s3Service.uploadFile(request.getPhoto(), "photos");

        Photo photo = Photo.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .tags(request.getTags())
                .photoUrl(photoUrl)
                .user(user)
                .build();
        photoRepository.save(photo);

        return new PhotoResponseDto(photo, false);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PhotoResponseDto> getAllPhotos() {
        return photoRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(photo -> new PhotoResponseDto(photo, false))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PhotoResponseDto getPhotoById(Long photoId, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new RuntimeException("Photo not found"));

        boolean likedByCurrentUser = false;
        if (user != null) {
            likedByCurrentUser = likeRepository.existsByPhotoAndUser(photo, user);
        }

        return new PhotoResponseDto(photo, likedByCurrentUser);
    }

    @Override
    public List<PhotoResponseDto> getMyPhotos(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return photoRepository.findByUser(user)
                .stream()
                .map(photo -> new PhotoResponseDto(photo, false))
                .collect(Collectors.toList());
    }
}
