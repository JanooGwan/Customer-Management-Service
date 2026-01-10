package com.example.CustomerManagementService.domain.category.controller;

import com.example.CustomerManagementService.domain.category.dto.request.CategoryCreateRequest;
import com.example.CustomerManagementService.domain.category.dto.request.CategoryUpdateRequest;
import com.example.CustomerManagementService.domain.category.dto.response.CategoryResponse;
import com.example.CustomerManagementService.domain.category.service.CategoryService;
import com.example.CustomerManagementService.global.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> create(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody CategoryCreateRequest request) {
        CategoryResponse response = categoryService.create(userDetails.getUserId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findAll(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<CategoryResponse> response = categoryService.findAllByUserId(userDetails.getUserId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> findById(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long categoryId) {
        CategoryResponse response = categoryService.findById(userDetails.getUserId(), categoryId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> update(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long categoryId,
            @Valid @RequestBody CategoryUpdateRequest request) {
        CategoryResponse response = categoryService.update(userDetails.getUserId(), categoryId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long categoryId) {
        categoryService.delete(userDetails.getUserId(), categoryId);
        return ResponseEntity.noContent().build();
    }
}
