package com.example.CustomerManagementService.domain.customer.dto.response;

import com.example.CustomerManagementService.domain.customer.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {

    private Long id;
    private String name;
    private String phone;
    private String email;
    private Long categoryId;
    private String categoryName;
    private LocalDateTime lastContactAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CustomerResponse from(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .phone(customer.getPhone())
                .email(customer.getEmail())
                .categoryId(customer.getCategory() != null ? customer.getCategory().getId() : null)
                .categoryName(customer.getCategory() != null ? customer.getCategory().getName() : null)
                .lastContactAt(customer.getLastContactAt())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }
}
