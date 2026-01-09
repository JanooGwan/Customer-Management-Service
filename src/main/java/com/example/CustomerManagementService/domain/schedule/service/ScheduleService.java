package com.example.CustomerManagementService.domain.schedule.service;

import com.example.CustomerManagementService.domain.customer.entity.Customer;
import com.example.CustomerManagementService.domain.customer.repository.CustomerRepository;
import com.example.CustomerManagementService.domain.schedule.dto.request.ScheduleCreateRequest;
import com.example.CustomerManagementService.domain.schedule.dto.request.ScheduleUpdateRequest;
import com.example.CustomerManagementService.domain.schedule.dto.response.ScheduleResponse;
import com.example.CustomerManagementService.domain.schedule.entity.Schedule;
import com.example.CustomerManagementService.domain.schedule.repository.ScheduleRepository;
import com.example.CustomerManagementService.global.exception.CustomException;
import com.example.CustomerManagementService.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    public ScheduleResponse create(Long userId, ScheduleCreateRequest request) {
        Customer customer = customerRepository.findByUserIdAndIdAndDeletedAtIsNull(userId, request.getCustomerId())
                .orElseThrow(() -> new CustomException(ErrorCode.CUSTOMER_NOT_FOUND));

        Schedule schedule = request.toEntity(customer);
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return ScheduleResponse.from(savedSchedule);
    }

    public ScheduleResponse findById(Long scheduleId) {
        Schedule schedule = scheduleRepository.findByIdAndDeletedAtIsNull(scheduleId)
                .orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));
        return ScheduleResponse.from(schedule);
    }

    public List<ScheduleResponse> findByCustomerId(Long userId, Long customerId) {
        Customer customer = customerRepository.findByUserIdAndIdAndDeletedAtIsNull(userId, customerId)
                .orElseThrow(() -> new CustomException(ErrorCode.CUSTOMER_NOT_FOUND));

        List<Schedule> schedules = scheduleRepository.findByCustomerIdAndDeletedAtIsNullOrderByStartTimeAsc(customerId);
        return schedules.stream()
                .map(ScheduleResponse::from)
                .collect(Collectors.toList());
    }

    public List<ScheduleResponse> findByDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        List<Schedule> schedules = scheduleRepository.findByUserIdAndStartTimeBetween(userId, startDate, endDate);
        return schedules.stream()
                .map(ScheduleResponse::from)
                .collect(Collectors.toList());
    }

    public List<ScheduleResponse> findTodaySchedules(Long userId) {
        LocalDateTime startOfDay = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.now().with(LocalTime.MAX);
        return findByDateRange(userId, startOfDay, endOfDay);
    }

    public List<ScheduleResponse> findTomorrowSchedules(Long userId) {
        LocalDateTime startOfTomorrow = LocalDateTime.now().plusDays(1).with(LocalTime.MIN);
        LocalDateTime endOfTomorrow = LocalDateTime.now().plusDays(1).with(LocalTime.MAX);
        return findByDateRange(userId, startOfTomorrow, endOfTomorrow);
    }

    public List<ScheduleResponse> findNotDoneSchedules(Long userId) {
        List<Schedule> schedules = scheduleRepository.findNotDoneSchedulesByUserId(userId);
        return schedules.stream()
                .map(ScheduleResponse::from)
                .collect(Collectors.toList());
    }

    public List<ScheduleResponse> findByPriority(Long userId, LocalDateTime startDate, LocalDateTime endDate, Schedule.Priority priority) {
        List<Schedule> schedules = scheduleRepository.findByUserIdAndStartTimeBetweenAndPriority(userId, startDate, endDate, priority);
        return schedules.stream()
                .map(ScheduleResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public ScheduleResponse update(Long userId, Long scheduleId, ScheduleUpdateRequest request) {
        Schedule schedule = scheduleRepository.findByIdAndDeletedAtIsNull(scheduleId)
                .orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));

        customerRepository.findByUserIdAndIdAndDeletedAtIsNull(userId, schedule.getCustomer().getId())
                .orElseThrow(() -> new CustomException(ErrorCode.CUSTOMER_NOT_FOUND));

        if (request.getTitle() != null || request.getContent() != null ||
            request.getStartTime() != null || request.getEndTime() != null || request.getPriority() != null) {

            String title = request.getTitle() != null ? request.getTitle() : schedule.getTitle();
            String content = request.getContent() != null ? request.getContent() : schedule.getContent();
            LocalDateTime startTime = request.getStartTime() != null ? request.getStartTime() : schedule.getStartTime();
            LocalDateTime endTime = request.getEndTime() != null ? request.getEndTime() : schedule.getEndTime();
            Schedule.Priority priority = request.getPriority() != null ? request.getPriority() : schedule.getPriority();

            schedule.updateSchedule(title, content, startTime, endTime, priority);
        }

        if (request.getIsDone() != null) {
            if (request.getIsDone()) {
                schedule.markAsDone();
            } else {
                schedule.markAsNotDone();
            }
        }

        return ScheduleResponse.from(schedule);
    }

    @Transactional
    public void toggleDone(Long userId, Long scheduleId) {
        Schedule schedule = scheduleRepository.findByIdAndDeletedAtIsNull(scheduleId)
                .orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));

        customerRepository.findByUserIdAndIdAndDeletedAtIsNull(userId, schedule.getCustomer().getId())
                .orElseThrow(() -> new CustomException(ErrorCode.CUSTOMER_NOT_FOUND));

        schedule.toggleDone();
    }

    @Transactional
    public void delete(Long userId, Long scheduleId) {
        Schedule schedule = scheduleRepository.findByIdAndDeletedAtIsNull(scheduleId)
                .orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));

        customerRepository.findByUserIdAndIdAndDeletedAtIsNull(userId, schedule.getCustomer().getId())
                .orElseThrow(() -> new CustomException(ErrorCode.CUSTOMER_NOT_FOUND));

        schedule.delete();
    }
}
