package com.example.pe.service;

import com.example.pe.dto.request.LoginRequestDto;
import com.example.pe.dto.request.SignupRequestDto;
import com.example.pe.dto.response.AuthResponseDto;
import com.example.pe.entity.User;
import com.example.pe.repository.UserRepository;
import com.example.pe.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

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
}
