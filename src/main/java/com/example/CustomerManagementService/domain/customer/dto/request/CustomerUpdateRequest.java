package com.example.CustomerManagementService.domain.customer.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUpdateRequest {

    @Size(max = 50, message = "이름은 최대 50자까지 가능합니다")
    private String name;

    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "올바른 전화번호 형식이 아닙니다 (예: 010-1234-5678)")
    private String phone;

    @Email(message = "올바른 이메일 형식이 아닙니다")
    private String email;

    private Long categoryId;
}
