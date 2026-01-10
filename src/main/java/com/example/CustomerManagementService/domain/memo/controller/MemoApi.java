package com.example.CustomerManagementService.domain.memo.controller;

import com.example.CustomerManagementService.domain.memo.dto.request.MemoCreateRequest;
import com.example.CustomerManagementService.domain.memo.dto.request.MemoUpdateRequest;
import com.example.CustomerManagementService.domain.memo.dto.response.MemoResponse;
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

@Tag(name = "Memo", description = "고객 메모 관리 API")
public interface MemoApi {

    @Operation(summary = "메모 생성", description = "고객에 대한 새로운 메모를 생성합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "메모 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력값"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "404", description = "고객을 찾을 수 없음")
    })
    ResponseEntity<MemoResponse> create(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "고객 ID") @PathVariable Long customerId,
            @Valid @RequestBody MemoCreateRequest request);

    @Operation(summary = "메모 목록 조회", description = "특정 고객의 모든 메모를 조회합니다 (최근 수정순)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "404", description = "고객을 찾을 수 없음")
    })
    ResponseEntity<List<MemoResponse>> findAll(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "고객 ID") @PathVariable Long customerId);

    @Operation(summary = "메모 수정", description = "메모 내용을 수정합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력값"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "404", description = "메모를 찾을 수 없음")
    })
    ResponseEntity<MemoResponse> update(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "고객 ID") @PathVariable Long customerId,
            @Parameter(description = "메모 ID") @PathVariable Long memoId,
            @Valid @RequestBody MemoUpdateRequest request);

    @Operation(summary = "메모 삭제", description = "메모를 삭제합니다 (Hard Delete)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "404", description = "메모를 찾을 수 없음")
    })
    ResponseEntity<Void> delete(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "고객 ID") @PathVariable Long customerId,
            @Parameter(description = "메모 ID") @PathVariable Long memoId);
}
