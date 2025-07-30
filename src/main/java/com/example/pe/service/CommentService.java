package com.example.pe.service;

import com.example.pe.dto.request.CommentRequestDto;
import com.example.pe.dto.response.CommentResponseDto;

import java.util.List;

public interface CommentService {
    CommentResponseDto addComment(Long photoId, CommentRequestDto request, String userEmail);
    List<CommentResponseDto> getComments(Long photoId);
    void deleteComment(Long commentId, String userEmail);
}
