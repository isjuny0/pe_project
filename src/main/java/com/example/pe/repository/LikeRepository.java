package com.example.pe.repository;

import com.example.pe.entity.Like;
import com.example.pe.entity.Photo;
import com.example.pe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    int countByPhoto(Photo photo);
    Optional<Like> findByPhotoAndUser(Photo photo, User user);
}
