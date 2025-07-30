package com.example.pe.service.impl;

import com.example.pe.dto.request.CommentRequestDto;
import com.example.pe.dto.response.CommentResponseDto;
import com.example.pe.entity.Comment;
import com.example.pe.entity.Photo;
import com.example.pe.entity.User;
import com.example.pe.repository.CommentRepository;
import com.example.pe.repository.PhotoRepository;
import com.example.pe.repository.UserRepository;
import com.example.pe.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PhotoRepository photoRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public CommentResponseDto addComment(Long photoId, CommentRequestDto request, String userEmail) {
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new RuntimeException("Not found photo"));
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Not found user"));

        Comment comment = Comment.builder()
                .photo(photo)
                .user(user)
                .content(request.getContent())
                .build();
        commentRepository.save(comment);

        return new CommentResponseDto(comment.getId(), comment.getContent(),
                user.getNickname(), comment.getCreatedAt());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponseDto> getComments(Long photoId) {
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new RuntimeException("Not found photo"));

        return commentRepository.findByPhoto(photo)
                .stream()
                .map(c -> new CommentResponseDto(c.getId(), c.getContent(),
                        c.getUser().getNickname(), c.getCreatedAt()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId, String userEmail) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Not found comment"));

        if (!comment.getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("Not authorized to delete comment");
        }
        commentRepository.delete(comment);
    }
}
