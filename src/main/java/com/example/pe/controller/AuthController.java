package com.example.pe.controller;

import com.example.pe.dto.request.LoginRequestDto;
import com.example.pe.dto.request.SignupRequestDto;
import com.example.pe.dto.response.AuthResponseDto;
import com.example.pe.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequestDto request) {
        userService.signup(request);
        return ResponseEntity.ok("Signup successful");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        AuthResponseDto response = userService.login(request);
        return ResponseEntity.ok(response);
    }
}
