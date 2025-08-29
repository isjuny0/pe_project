package com.example.pe.repository;

import com.example.pe.entity.Photo;
import com.example.pe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findByUser(User user);
    List<Photo> findAllByOrderByCreatedAtDesc();
    List<Photo> findAllByOrderByLikesDesc();
    List<Photo> findByUserIdOrderByCreatedAtDesc(Long userId);
}
