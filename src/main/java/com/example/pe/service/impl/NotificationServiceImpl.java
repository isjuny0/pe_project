package com.example.pe.service.impl;

import com.example.pe.dto.notification.NotificationDto;
import com.example.pe.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendLikeNotification(Long photoId, String fromUser, String toUser) {
        String destination = "/topic/notifications/" + toUser;
        NotificationDto dto = new NotificationDto(fromUser + "님이 사진을 좋아합니다.", photoId, fromUser);
        messagingTemplate.convertAndSend(destination, dto);
    }

}
