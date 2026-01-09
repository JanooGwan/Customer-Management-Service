package com.example.CustomerManagementService.domain.schedule.dto.response;

import com.example.CustomerManagementService.domain.schedule.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponse {

    private Long id;
    private Long customerId;
    private String customerName;
    private String title;
    private String content;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean isDone;
    private String priority;
    private Boolean reminderSent;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ScheduleResponse from(Schedule schedule) {
        return ScheduleResponse.builder()
                .id(schedule.getId())
                .customerId(schedule.getCustomer().getId())
                .customerName(schedule.getCustomer().getName())
                .title(schedule.getTitle())
                .content(schedule.getContent())
                .startTime(schedule.getStartTime())
                .endTime(schedule.getEndTime())
                .isDone(schedule.getIsDone())
                .priority(schedule.getPriority().name())
                .reminderSent(schedule.getReminderSent())
                .createdAt(schedule.getCreatedAt())
                .updatedAt(schedule.getUpdatedAt())
                .build();
    }
}
