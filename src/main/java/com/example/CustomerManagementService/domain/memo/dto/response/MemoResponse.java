package com.example.CustomerManagementService.domain.memo.dto.response;

import com.example.CustomerManagementService.domain.memo.entity.Memo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemoResponse {

    private Long id;
    private Long customerId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static MemoResponse from(Memo memo) {
        return MemoResponse.builder()
                .id(memo.getId())
                .customerId(memo.getCustomer().getId())
                .content(memo.getContent())
                .createdAt(memo.getCreatedAt())
                .updatedAt(memo.getUpdatedAt())
                .build();
    }
}
