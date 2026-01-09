package com.example.CustomerManagementService.domain.schedule.dto.request;

import com.example.CustomerManagementService.domain.schedule.entity.Schedule;
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
public class ScheduleUpdateRequest {

    @Size(max = 100, message = "제목은 최대 100자까지 가능합니다")
    private String title;

    private String content;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Schedule.Priority priority;

    private Boolean isDone;
}
