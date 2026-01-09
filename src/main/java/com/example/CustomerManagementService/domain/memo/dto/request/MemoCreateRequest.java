package com.example.CustomerManagementService.domain.memo.dto.request;

import com.example.CustomerManagementService.domain.customer.entity.Customer;
import com.example.CustomerManagementService.domain.memo.entity.Memo;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemoCreateRequest {

    @NotBlank(message = "메모 내용은 필수입니다")
    private String content;

    public Memo toEntity(Customer customer) {
        return Memo.builder()
                .customer(customer)
                .content(this.content)
                .build();
    }
}
