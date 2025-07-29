package com.example.pe.service.impl;

import com.example.pe.dto.request.PhotoUploadRequestDto;
import com.example.pe.dto.response.PhotoResponseDto;
import com.example.pe.entity.Photo;
import com.example.pe.entity.User;
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

        return new PhotoResponseDto(photo.getId(), photo.getTitle(), photo.getDescription(),
                photo.getTags(), photo.getPhotoUrl(), user.getNickname(),
                0, 0, photo.getCreatedAt());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PhotoResponseDto> getAllPhotos() {
        return photoRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(photo -> new PhotoResponseDto(photo.getId(), photo.getTitle(),
                        photo.getDescription(), photo.getTags(), photo.getPhotoUrl(),
                        photo.getUser().getNickname(),
                        photo.getLikes().size(),
                        photo.getComments().size(),
                        photo.getCreatedAt()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PhotoResponseDto getPhotoById(Long photoId) {
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new RuntimeException("Photo not found"));
        return new PhotoResponseDto(photo.getId(), photo.getTitle(), photo.getDescription(),
                photo.getTags(), photo.getPhotoUrl(), photo.getUser().getNickname(),
                photo.getLikes().size(), photo.getComments().size(), photo.getCreatedAt());
    }
}
