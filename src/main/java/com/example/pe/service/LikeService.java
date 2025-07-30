package com.example.pe.service;

public interface LikeService {
    boolean toggleLike(Long photoId, String userEmail);
    int getLikeCount(Long photoId);
}
