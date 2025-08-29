package com.example.pe.service;

import com.example.pe.dto.request.GalleryUpdateRequestDto;
import com.example.pe.dto.request.LoginRequestDto;
import com.example.pe.dto.request.SignupRequestDto;
import com.example.pe.dto.response.AuthResponseDto;
import com.example.pe.dto.response.GalleryInfoResponseDto;
import com.example.pe.dto.response.GalleryListItemDto;
import com.example.pe.dto.response.UserResponseDto;

import java.util.List;

public interface UserService {
    void signup(SignupRequestDto request);
    AuthResponseDto login(LoginRequestDto request);
    UserResponseDto getUserInfo(String email);
    GalleryInfoResponseDto getUserGallery(Long userId);
    void updateUserGallery(String userEmail, GalleryUpdateRequestDto request);
    List<GalleryListItemDto> getAllGalleries();
}
