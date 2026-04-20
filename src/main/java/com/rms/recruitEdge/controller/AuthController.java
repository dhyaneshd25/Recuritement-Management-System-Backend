package com.rms.recruitEdge.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rms.recruitEdge.dto.AuthResponse;
import com.rms.recruitEdge.dto.GoogleLoginRequest;
import com.rms.recruitEdge.dto.LoginRequest;
import com.rms.recruitEdge.dto.RegisterRequest;
import com.rms.recruitEdge.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
       
        AuthResponse res = authService.register(request);
       
        return res;
    }


    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {

        return authService.login(request);
    }

    @PostMapping("/googleLogin")
    public AuthResponse googleLogin(@RequestBody GoogleLoginRequest req) throws Exception{

        return authService.loginWithGoogle(req);
    }
}