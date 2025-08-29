package com.example.pe.service.impl;

import com.example.pe.dto.request.GalleryUpdateRequestDto;
import com.example.pe.dto.request.LoginRequestDto;
import com.example.pe.dto.request.SignupRequestDto;
import com.example.pe.dto.response.*;
import com.example.pe.entity.Photo;
import com.example.pe.entity.User;
import com.example.pe.repository.PhotoRepository;
import com.example.pe.repository.UserRepository;
import com.example.pe.security.JwtTokenProvider;
import com.example.pe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PhotoRepository photoRepository;

    @Override
    public void signup(SignupRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("이미 사용 중인 이메일입니다.");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("이미 사용 중인 닉네임입니다.");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .build();
        userRepository.save(user);
    }

    @Override
    public AuthResponseDto login(LoginRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("가입되지 않은 이메일 입니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtTokenProvider.generateToken(request.getEmail());
        return new AuthResponseDto(token);
    }

    @Override
    public UserResponseDto getUserInfo(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Not found User"));
        return new UserResponseDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public GalleryInfoResponseDto getUserGallery(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Not found User"));

        List<PhotoResponseDto> photos = photoRepository.findByUserIdOrderByCreatedAtDesc(user.getId())
                .stream()
                .map(photo -> new PhotoResponseDto(photo, false))
                .collect(Collectors.toList());

        Photo repPhoto = user.getRepresentativePhoto();
        return new GalleryInfoResponseDto(
                user.getId(),
                user.getNickname(),
                repPhoto == null ? null :
                        new PhotoResponseDto(repPhoto, true),
                user.getGalleryTheme(),
                photos
        );
    }

    @Override
    @Transactional
    public void updateUserGallery(String userEmail, GalleryUpdateRequestDto request) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Not found User"));

        if(request.getRepresentativePhotoId() != null) {
            Photo photo = photoRepository.findById(request.getRepresentativePhotoId())
                    .orElseThrow(() -> new RuntimeException("Not found Photo"));
            user.setRepresentativePhoto(photo);
        }

        if(request.getGalleryTheme() != null) {
            user.setGalleryTheme(request.getGalleryTheme());
        }

        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GalleryListItemDto> getAllGalleries() {
        List<User> users = userRepository.findAll();

        return users.stream().map(user -> {
            Photo repPhoto = user.getRepresentativePhoto();
            PhotoResponseDto repPhotoDto = null;

            if (repPhoto != null) {
                repPhotoDto = new PhotoResponseDto(
                        repPhoto, false
                );
            }

            return new GalleryListItemDto(
                    user.getId(),
                    user.getNickname(),
                    repPhotoDto,
                    user.getGalleryTheme(),
                    user.getGalleryVisitCount()
            );
        }).collect(Collectors.toList());
    }
}
