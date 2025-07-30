package com.example.pe.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationDto {
    private String message;
    private Long photoId;
    private String fromUser;
}
