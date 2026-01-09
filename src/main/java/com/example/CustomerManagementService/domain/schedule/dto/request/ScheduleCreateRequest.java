package com.example.CustomerManagementService.domain.schedule.dto.request;

import com.example.CustomerManagementService.domain.customer.entity.Customer;
import com.example.CustomerManagementService.domain.schedule.entity.Schedule;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleCreateRequest {

    @NotNull(message = "고객 ID는 필수입니다")
    private Long customerId;

    @NotBlank(message = "일정 제목은 필수입니다")
    @Size(max = 100, message = "제목은 최대 100자까지 가능합니다")
    private String title;

    private String content;

    @NotNull(message = "시작 시간은 필수입니다")
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Builder.Default
    private Schedule.Priority priority = Schedule.Priority.MEDIUM;

    public Schedule toEntity(Customer customer) {
        return Schedule.builder()
                .customer(customer)
                .title(this.title)
                .content(this.content)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .priority(this.priority)
                .isDone(false)
                .reminderSent(false)
                .build();
    }
}
