package com.example.pe.service.impl;

import com.example.pe.entity.Like;
import com.example.pe.entity.Photo;
import com.example.pe.entity.User;
import com.example.pe.repository.LikeRepository;
import com.example.pe.repository.PhotoRepository;
import com.example.pe.repository.UserRepository;
import com.example.pe.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final PhotoRepository photoRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public boolean toggleLike(Long photoId, String userEmail) {
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new RuntimeException("Not found photo"));
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Not found user"));

        return likeRepository.findByPhotoAndUser(photo, user)
                .map(existing -> {
                    likeRepository.delete(existing);
                    return false;
                })
                .orElseGet(() -> {
                    Like like = Like.builder()
                            .photo(photo)
                            .user(user)
                            .build();
                    likeRepository.save(like);
                    return true;
                });
    }

    @Override
    @Transactional(readOnly = true)
    public int getLikeCount(Long photoId) {
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new RuntimeException("Not found photo"));
        return likeRepository.countByPhoto(photo);
    }

}
