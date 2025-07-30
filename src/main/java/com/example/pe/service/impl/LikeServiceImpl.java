package com.example.pe.service.impl;

import com.example.pe.entity.Like;
import com.example.pe.entity.Photo;
import com.example.pe.entity.User;
import com.example.pe.repository.LikeRepository;
import com.example.pe.repository.PhotoRepository;
import com.example.pe.repository.UserRepository;
import com.example.pe.service.LikeService;
import com.example.pe.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final PhotoRepository photoRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

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

                    // 알림 발송 (본인 사진에 좋아요 누르면 제외)
                    if (!photo.getUser().getEmail().equals(userEmail)) {
                        notificationService.sendLikeNotification(photo.getId(), user.getNickname(), photo.getUser().getEmail());
                        log.info("send like notification");
                    }
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
