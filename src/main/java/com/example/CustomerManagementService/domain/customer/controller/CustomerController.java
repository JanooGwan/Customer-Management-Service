package com.example.CustomerManagementService.domain.customer.controller;

import com.example.CustomerManagementService.domain.customer.dto.request.CustomerCreateRequest;
import com.example.CustomerManagementService.domain.customer.dto.request.CustomerSearchRequest;
import com.example.CustomerManagementService.domain.customer.dto.request.CustomerUpdateRequest;
import com.example.CustomerManagementService.domain.customer.dto.response.CustomerResponse;
import com.example.CustomerManagementService.domain.customer.service.CustomerService;
import com.example.CustomerManagementService.global.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> create(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody CustomerCreateRequest request) {
        CustomerResponse response = customerService.create(userDetails.getUserId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findAll(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<CustomerResponse> response = customerService.findAll(userDetails.getUserId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> findById(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long customerId) {
        CustomerResponse response = customerService.findById(userDetails.getUserId(), customerId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CustomerResponse>> search(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ModelAttribute CustomerSearchRequest request) {
        List<CustomerResponse> response = customerService.search(userDetails.getUserId(), request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<CustomerResponse>> findByCategory(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long categoryId) {
        List<CustomerResponse> response = customerService.findByCategory(userDetails.getUserId(), categoryId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> update(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long customerId,
            @Valid @RequestBody CustomerUpdateRequest request) {
        CustomerResponse response = customerService.update(userDetails.getUserId(), customerId, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{customerId}/last-contact")
    public ResponseEntity<Void> updateLastContactAt(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long customerId) {
        customerService.updateLastContactAt(userDetails.getUserId(), customerId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long customerId) {
        customerService.delete(userDetails.getUserId(), customerId);
        return ResponseEntity.noContent().build();
    }
}
