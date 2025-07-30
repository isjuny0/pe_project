package com.example.pe.service;

public interface NotificationService {
    void sendLikeNotification(Long photoId, String fromUser, String toUser);
}
