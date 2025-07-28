package com.example.pe.repository;

import com.example.pe.entity.Comment;
import com.example.pe.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPhoto(Photo photo);
}
