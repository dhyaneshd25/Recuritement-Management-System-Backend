package com.rms.recruitEdge.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rms.recruitEdge.config.JwtUtil;
import com.rms.recruitEdge.dto.AuthResponse;
import com.rms.recruitEdge.dto.LoginRequest;
import com.rms.recruitEdge.dto.RegisterRequest;
import com.rms.recruitEdge.entity.User;
import com.rms.recruitEdge.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    public void register(RegisterRequest request) {

        User user = User.builder()
                        .email(request.getEmail())  
                        .name(request.getName())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(request.getRole())
                        .build();

        userRepository.save(user);
    }


    public AuthResponse login(LoginRequest request) {
System.out.println("checked3.............");
         try {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

    } catch (Exception e) {
        throw new RuntimeException("Invalid email or password");
    }

        String token = jwtUtil.generateToken(request.getEmail());

        return new AuthResponse(token);
    }
}
