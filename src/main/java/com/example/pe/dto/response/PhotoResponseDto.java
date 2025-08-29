package com.example.pe.dto.response;

import com.example.pe.entity.Photo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PhotoResponseDto {
    private Long id;
    private String title;
    private String description;
    private String tags;
    private String photoUrl;
    private String userNickname;
    private int likeCount;
    private int commentCount;
    private boolean likedByCurrentUser;
    private LocalDateTime createdAt;

    public PhotoResponseDto(Photo photo, boolean likedByCurrentUser) {
        this.id = photo.getId();
        this.title = photo.getTitle();
        this.description = photo.getDescription();
        this.tags = photo.getTags();
        this.photoUrl = photo.getPhotoUrl();
        this.userNickname = photo.getUser().getNickname();
        this.likeCount = photo.getLikes().size();
        this.commentCount = photo.getComments().size();
        this.likedByCurrentUser = likedByCurrentUser;
        this.createdAt = photo.getCreatedAt();
    }
}
