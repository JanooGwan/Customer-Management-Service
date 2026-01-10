package com.example.CustomerManagementService.domain.memo.controller;

import com.example.CustomerManagementService.domain.memo.dto.request.MemoCreateRequest;
import com.example.CustomerManagementService.domain.memo.dto.request.MemoUpdateRequest;
import com.example.CustomerManagementService.domain.memo.dto.response.MemoResponse;
import com.example.CustomerManagementService.domain.memo.service.MemoService;
import com.example.CustomerManagementService.global.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers/{customerId}/memos")
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    @PostMapping
    public ResponseEntity<MemoResponse> create(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long customerId,
            @Valid @RequestBody MemoCreateRequest request) {
        MemoResponse response = memoService.create(userDetails.getUserId(), customerId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<MemoResponse>> findAll(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long customerId) {
        List<MemoResponse> response = memoService.findAllByCustomerId(userDetails.getUserId(), customerId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{memoId}")
    public ResponseEntity<MemoResponse> update(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long customerId,
            @PathVariable Long memoId,
            @Valid @RequestBody MemoUpdateRequest request) {
        MemoResponse response = memoService.update(userDetails.getUserId(), customerId, memoId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{memoId}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long customerId,
            @PathVariable Long memoId) {
        memoService.delete(userDetails.getUserId(), customerId, memoId);
        return ResponseEntity.noContent().build();
    }
}
