package com.example.CustomerManagementService.domain.customer.controller;

import com.example.CustomerManagementService.domain.customer.dto.request.CustomerCreateRequest;
import com.example.CustomerManagementService.domain.customer.dto.request.CustomerSearchRequest;
import com.example.CustomerManagementService.domain.customer.dto.request.CustomerUpdateRequest;
import com.example.CustomerManagementService.domain.customer.dto.response.CustomerResponse;
import com.example.CustomerManagementService.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Customer", description = "고객 관리 API")
public interface CustomerApi {

    @Operation(summary = "고객 생성", description = "새로운 고객을 등록합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "고객 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력값"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 전화번호")
    })
    ResponseEntity<CustomerResponse> create(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody CustomerCreateRequest request);

    @Operation(summary = "고객 목록 조회", description = "사용자의 모든 고객을 조회합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요")
    })
    ResponseEntity<List<CustomerResponse>> findAll(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails);

    @Operation(summary = "고객 상세 조회", description = "특정 고객을 조회합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "404", description = "고객을 찾을 수 없음")
    })
    ResponseEntity<CustomerResponse> findById(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "고객 ID") @PathVariable Long customerId);

    @Operation(summary = "고객 검색", description = "키워드, 카테고리, 연락일 범위로 고객을 검색합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "검색 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요")
    })
    ResponseEntity<List<CustomerResponse>> search(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails,
            @ModelAttribute CustomerSearchRequest request);

    @Operation(summary = "카테고리별 고객 조회", description = "특정 카테고리의 모든 고객을 조회합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요")
    })
    ResponseEntity<List<CustomerResponse>> findByCategory(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "카테고리 ID") @PathVariable Long categoryId);

    @Operation(summary = "고객 정보 수정", description = "고객 정보를 수정합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력값"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "404", description = "고객을 찾을 수 없음"),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 전화번호")
    })
    ResponseEntity<CustomerResponse> update(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "고객 ID") @PathVariable Long customerId,
            @Valid @RequestBody CustomerUpdateRequest request);

    @Operation(summary = "마지막 연락일 업데이트", description = "고객의 마지막 연락일을 현재 시간으로 업데이트합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "업데이트 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "404", description = "고객을 찾을 수 없음")
    })
    ResponseEntity<Void> updateLastContactAt(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "고객 ID") @PathVariable Long customerId);

    @Operation(summary = "고객 삭제", description = "고객을 삭제합니다 (Soft Delete)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "404", description = "고객을 찾을 수 없음")
    })
    ResponseEntity<Void> delete(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "고객 ID") @PathVariable Long customerId);
}
