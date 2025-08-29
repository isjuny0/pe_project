package com.example.pe.dto.response;

import com.example.pe.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String content;
    private String userNickname;
    private LocalDateTime createdAt;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.userNickname = comment.getUser().getNickname();
        this.createdAt = comment.getCreatedAt();
    }
}
