package com.example.CustomerManagementService.domain.category.controller;

import com.example.CustomerManagementService.domain.category.dto.request.CategoryCreateRequest;
import com.example.CustomerManagementService.domain.category.dto.request.CategoryUpdateRequest;
import com.example.CustomerManagementService.domain.category.dto.response.CategoryResponse;
import com.example.CustomerManagementService.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Category", description = "카테고리 관리 API")
public interface CategoryApi {

    @Operation(summary = "카테고리 생성", description = "새로운 카테고리를 생성합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "카테고리 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력값"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 카테고리명")
    })
    ResponseEntity<CategoryResponse> create(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody CategoryCreateRequest request);

    @Operation(summary = "카테고리 목록 조회", description = "사용자의 모든 카테고리를 조회합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요")
    })
    ResponseEntity<List<CategoryResponse>> findAll(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails);

    @Operation(summary = "카테고리 상세 조회", description = "특정 카테고리를 조회합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "404", description = "카테고리를 찾을 수 없음")
    })
    ResponseEntity<CategoryResponse> findById(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "카테고리 ID") @PathVariable Long categoryId);

    @Operation(summary = "카테고리 수정", description = "카테고리 정보를 수정합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력값"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "404", description = "카테고리를 찾을 수 없음"),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 카테고리명")
    })
    ResponseEntity<CategoryResponse> update(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "카테고리 ID") @PathVariable Long categoryId,
            @Valid @RequestBody CategoryUpdateRequest request);

    @Operation(summary = "카테고리 삭제", description = "카테고리를 삭제합니다 (Soft Delete)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "404", description = "카테고리를 찾을 수 없음")
    })
    ResponseEntity<Void> delete(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "카테고리 ID") @PathVariable Long categoryId);
}
