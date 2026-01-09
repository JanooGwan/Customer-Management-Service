package com.example.CustomerManagementService.domain.customer.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSearchRequest {

    private String keyword;
    private Long categoryId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
