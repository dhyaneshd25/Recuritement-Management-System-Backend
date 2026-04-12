package com.rms.recruitEdge.service;


import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.rms.recruitEdge.config.JwtUtil;
import com.rms.recruitEdge.dto.AuthResponse;
import com.rms.recruitEdge.dto.LoginRequest;
import com.rms.recruitEdge.dto.RegisterRequest;
import com.rms.recruitEdge.dto.UserDto;
import com.rms.recruitEdge.entity.Role;
import com.rms.recruitEdge.entity.User;
import com.rms.recruitEdge.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${google.client-id}")
    private String googleClientId;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest request) {

        User user = User.builder()
                        .email(request.getEmail())  
                        .name(request.getName())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(request.getRole())
                        .build();

        userRepository.save(user);

         String token = jwtUtil.generateToken(request.getEmail());


        return new AuthResponse(token,new UserDto(user.getId(), user.getEmail(), user.getName(), user.getRole().name()));
    }


    public AuthResponse login(LoginRequest request) {

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

        User user = userRepository.findByEmail(request.getEmail()).get();

        return new AuthResponse(token,new UserDto(user.getId(), user.getEmail(), user.getName(), user.getRole().name()));
    }


    public AuthResponse loginWithGoogle(String idToken) throws Exception {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(idToken);  // sends "Authorization: Bearer <token>"
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
            "https://www.googleapis.com/oauth2/v3/userinfo",
            HttpMethod.GET,
            entity,
            Map.class
        );

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new RuntimeException("Failed to fetch user info from Google");
        }

       
        Map<String, Object> userInfo = response.getBody();
        String email    = (String) userInfo.get("email");
        String name     = (String) userInfo.get("name");


        User user = userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName(name);
            newUser.setRole(Role.CANDIDATE);
            return userRepository.save(newUser);
        });

        String jwt = jwtUtil.generateToken(user.getEmail());

        UserDto userDTO = new UserDto(user.getId(), user.getEmail(), user.getName(), user.getRole().name());
        return new AuthResponse(jwt, userDTO);
    }
}
