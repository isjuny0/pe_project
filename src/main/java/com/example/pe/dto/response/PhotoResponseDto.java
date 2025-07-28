package com.example.pe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PhotoResponseDto {
    private Long id;
    private String title;
    private String description;
    private String tags;
    private String photoUrl;
    private String userNickname;
    private int likeCount;
    private int commentCount;
    private LocalDateTime createdAt;
}
