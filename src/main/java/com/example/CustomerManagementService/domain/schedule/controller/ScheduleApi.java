package com.example.CustomerManagementService.domain.schedule.controller;

import com.example.CustomerManagementService.domain.schedule.dto.request.ScheduleCreateRequest;
import com.example.CustomerManagementService.domain.schedule.dto.request.ScheduleUpdateRequest;
import com.example.CustomerManagementService.domain.schedule.dto.response.ScheduleResponse;
import com.example.CustomerManagementService.domain.schedule.entity.Schedule;
import com.example.CustomerManagementService.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Schedule", description = "일정 관리 API")
public interface ScheduleApi {

    @Operation(summary = "일정 생성", description = "새로운 일정을 생성합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "일정 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력값"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "404", description = "고객을 찾을 수 없음")
    })
    ResponseEntity<ScheduleResponse> create(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody ScheduleCreateRequest request);

    @Operation(summary = "일정 상세 조회", description = "특정 일정을 조회합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "일정을 찾을 수 없음")
    })
    ResponseEntity<ScheduleResponse> findById(
            @Parameter(description = "일정 ID") @PathVariable Long scheduleId);

    @Operation(summary = "고객별 일정 조회", description = "특정 고객의 모든 일정을 조회합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "404", description = "고객을 찾을 수 없음")
    })
    ResponseEntity<List<ScheduleResponse>> findByCustomerId(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "고객 ID") @PathVariable Long customerId);

    @Operation(summary = "캘린더 조회", description = "날짜 범위로 일정을 조회합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요")
    })
    ResponseEntity<List<ScheduleResponse>> findByDateRange(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "시작 날짜") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "종료 날짜") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate);

    @Operation(summary = "오늘 일정 조회", description = "오늘의 모든 일정을 조회합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요")
    })
    ResponseEntity<List<ScheduleResponse>> findTodaySchedules(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails);

    @Operation(summary = "내일 일정 조회", description = "내일의 모든 일정을 조회합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요")
    })
    ResponseEntity<List<ScheduleResponse>> findTomorrowSchedules(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails);

    @Operation(summary = "미완료 일정 조회", description = "완료되지 않은 모든 일정을 조회합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요")
    })
    ResponseEntity<List<ScheduleResponse>> findNotDoneSchedules(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails);

    @Operation(summary = "우선순위별 일정 조회", description = "날짜 범위와 우선순위로 일정을 조회합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요")
    })
    ResponseEntity<List<ScheduleResponse>> findByPriority(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "시작 날짜") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "종료 날짜") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @Parameter(description = "우선순위") @RequestParam Schedule.Priority priority);

    @Operation(summary = "일정 수정", description = "일정 정보를 수정합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력값"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "404", description = "일정을 찾을 수 없음")
    })
    ResponseEntity<ScheduleResponse> update(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "일정 ID") @PathVariable Long scheduleId,
            @Valid @RequestBody ScheduleUpdateRequest request);

    @Operation(summary = "완료 상태 토글", description = "일정의 완료 상태를 토글합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "토글 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "404", description = "일정을 찾을 수 없음")
    })
    ResponseEntity<Void> toggleDone(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "일정 ID") @PathVariable Long scheduleId);

    @Operation(summary = "일정 삭제", description = "일정을 삭제합니다 (Soft Delete)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "404", description = "일정을 찾을 수 없음")
    })
    ResponseEntity<Void> delete(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "일정 ID") @PathVariable Long scheduleId);
}
