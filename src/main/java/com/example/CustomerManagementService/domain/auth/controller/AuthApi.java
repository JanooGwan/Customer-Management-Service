package com.example.CustomerManagementService.domain.auth.controller;

import com.example.CustomerManagementService.domain.auth.dto.request.LoginRequest;
import com.example.CustomerManagementService.domain.auth.dto.request.SignupRequest;
import com.example.CustomerManagementService.domain.auth.dto.request.TokenRefreshRequest;
import com.example.CustomerManagementService.domain.auth.dto.response.LoginResponse;
import com.example.CustomerManagementService.domain.auth.dto.response.TokenResponse;
import com.example.CustomerManagementService.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Auth", description = "인증 관련 API")
public interface AuthApi {

    @Operation(summary = "회원가입", description = "새로운 사용자를 등록합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력값"),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 이메일")
    })
    ResponseEntity<Void> signup(@Valid @RequestBody SignupRequest request);

    @Operation(summary = "로그인", description = "이메일과 비밀번호로 로그인합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력값"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request);

    @Operation(summary = "토큰 갱신", description = "Refresh Token으로 새로운 Access Token을 발급받습니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "토큰 갱신 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력값"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 Refresh Token")
    })
    ResponseEntity<TokenResponse> refresh(@Valid @RequestBody TokenRefreshRequest request);

    @Operation(summary = "로그아웃", description = "로그아웃하고 Refresh Token을 무효화합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "로그아웃 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요")
    })
    ResponseEntity<Void> logout(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails);
}
