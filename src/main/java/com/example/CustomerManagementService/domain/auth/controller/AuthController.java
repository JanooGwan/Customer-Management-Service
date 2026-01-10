package com.example.CustomerManagementService.domain.auth.controller;

import com.example.CustomerManagementService.domain.auth.dto.request.LoginRequest;
import com.example.CustomerManagementService.domain.auth.dto.request.SignupRequest;
import com.example.CustomerManagementService.domain.auth.dto.request.TokenRefreshRequest;
import com.example.CustomerManagementService.domain.auth.dto.response.LoginResponse;
import com.example.CustomerManagementService.domain.auth.dto.response.TokenResponse;
import com.example.CustomerManagementService.domain.auth.service.AuthService;
import com.example.CustomerManagementService.global.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody SignupRequest request) {
        authService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@Valid @RequestBody TokenRefreshRequest request) {
        TokenResponse response = authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal CustomUserDetails userDetails) {
        authService.logout(userDetails.getUserId());
        return ResponseEntity.noContent().build();
    }
}
