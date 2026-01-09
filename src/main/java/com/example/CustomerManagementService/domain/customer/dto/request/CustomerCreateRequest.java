package com.example.CustomerManagementService.domain.customer.dto.request;

import com.example.CustomerManagementService.domain.auth.entity.User;
import com.example.CustomerManagementService.domain.category.entity.Category;
import com.example.CustomerManagementService.domain.customer.entity.Customer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class CustomerCreateRequest {

    @NotBlank(message = "고객 이름은 필수입니다")
    @Size(max = 50, message = "이름은 최대 50자까지 가능합니다")
    private String name;

    @NotBlank(message = "전화번호는 필수입니다")
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "올바른 전화번호 형식이 아닙니다 (예: 010-1234-5678)")
    private String phone;

    @Email(message = "올바른 이메일 형식이 아닙니다")
    private String email;

    private Long categoryId;

    public Customer toEntity(User user, Category category) {
        return Customer.builder()
                .user(user)
                .name(this.name)
                .phone(this.phone)
                .email(this.email)
                .category(category)
                .build();
    }
}
