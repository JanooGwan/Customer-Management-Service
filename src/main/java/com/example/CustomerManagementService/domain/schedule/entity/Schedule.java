package com.example.CustomerManagementService.domain.schedule.entity;

import com.example.CustomerManagementService.domain.customer.entity.Customer;
import com.example.CustomerManagementService.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "schedule",
    indexes = {
        @Index(name = "idx_customer_id", columnList = "customer_id"),
        @Index(name = "idx_start_time", columnList = "start_time")
    }
)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Schedule extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isDone = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private Priority priority = Priority.MEDIUM;

    @Column(nullable = false)
    @Builder.Default
    private Boolean reminderSent = false;

    public enum Priority {
        LOW, MEDIUM, HIGH
    }

    // 비즈니스 로직 메서드
    public void updateSchedule(String title, String content, LocalDateTime startTime, LocalDateTime endTime, Priority priority) {
        this.title = title;
        this.content = content;
        this.startTime = startTime;
        this.endTime = endTime;
        this.priority = priority;
    }

    public void toggleDone() {
        this.isDone = !this.isDone;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    public void markReminderSent() {
        this.reminderSent = true;
    }
}
