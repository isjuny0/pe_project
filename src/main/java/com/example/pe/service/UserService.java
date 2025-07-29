package com.example.pe.service;

import com.example.pe.dto.request.LoginRequestDto;
import com.example.pe.dto.request.SignupRequestDto;
import com.example.pe.dto.response.AuthResponseDto;

public interface UserService {
    void signup(SignupRequestDto request);
    AuthResponseDto login(LoginRequestDto request);
}
