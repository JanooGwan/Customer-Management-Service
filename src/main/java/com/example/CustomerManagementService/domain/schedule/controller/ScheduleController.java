package com.example.CustomerManagementService.domain.schedule.controller;

import com.example.CustomerManagementService.domain.schedule.dto.request.ScheduleCreateRequest;
import com.example.CustomerManagementService.domain.schedule.dto.request.ScheduleUpdateRequest;
import com.example.CustomerManagementService.domain.schedule.dto.response.ScheduleResponse;
import com.example.CustomerManagementService.domain.schedule.entity.Schedule;
import com.example.CustomerManagementService.domain.schedule.service.ScheduleService;
import com.example.CustomerManagementService.global.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponse> create(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody ScheduleCreateRequest request) {
        ScheduleResponse response = scheduleService.create(userDetails.getUserId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponse> findById(@PathVariable Long scheduleId) {
        ScheduleResponse response = scheduleService.findById(scheduleId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ScheduleResponse>> findByCustomerId(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long customerId) {
        List<ScheduleResponse> response = scheduleService.findByCustomerId(userDetails.getUserId(), customerId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/calendar")
    public ResponseEntity<List<ScheduleResponse>> findByDateRange(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<ScheduleResponse> response = scheduleService.findByDateRange(userDetails.getUserId(), startDate, endDate);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/today")
    public ResponseEntity<List<ScheduleResponse>> findTodaySchedules(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<ScheduleResponse> response = scheduleService.findTodaySchedules(userDetails.getUserId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tomorrow")
    public ResponseEntity<List<ScheduleResponse>> findTomorrowSchedules(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<ScheduleResponse> response = scheduleService.findTomorrowSchedules(userDetails.getUserId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/not-done")
    public ResponseEntity<List<ScheduleResponse>> findNotDoneSchedules(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<ScheduleResponse> response = scheduleService.findNotDoneSchedules(userDetails.getUserId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/priority")
    public ResponseEntity<List<ScheduleResponse>> findByPriority(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam Schedule.Priority priority) {
        List<ScheduleResponse> response = scheduleService.findByPriority(userDetails.getUserId(), startDate, endDate, priority);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponse> update(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long scheduleId,
            @Valid @RequestBody ScheduleUpdateRequest request) {
        ScheduleResponse response = scheduleService.update(userDetails.getUserId(), scheduleId, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{scheduleId}/toggle-done")
    public ResponseEntity<Void> toggleDone(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long scheduleId) {
        scheduleService.toggleDone(userDetails.getUserId(), scheduleId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long scheduleId) {
        scheduleService.delete(userDetails.getUserId(), scheduleId);
        return ResponseEntity.noContent().build();
    }
}
