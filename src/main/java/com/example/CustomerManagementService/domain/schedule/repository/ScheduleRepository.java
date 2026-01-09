package com.example.CustomerManagementService.domain.schedule.repository;

import com.example.CustomerManagementService.domain.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByCustomerIdAndDeletedAtIsNullOrderByStartTimeAsc(Long customerId);

    Optional<Schedule> findByIdAndDeletedAtIsNull(Long id);

    @Query("SELECT s FROM Schedule s WHERE s.customer.user.id = :userId AND s.deletedAt IS NULL " +
           "AND s.startTime BETWEEN :startDate AND :endDate ORDER BY s.startTime ASC")
    List<Schedule> findByUserIdAndStartTimeBetween(
        @Param("userId") Long userId,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT s FROM Schedule s WHERE s.customer.user.id = :userId AND s.deletedAt IS NULL " +
           "AND s.isDone = false ORDER BY s.startTime ASC")
    List<Schedule> findNotDoneSchedulesByUserId(@Param("userId") Long userId);

    @Query("SELECT s FROM Schedule s WHERE s.customer.user.id = :userId AND s.deletedAt IS NULL " +
           "AND s.startTime BETWEEN :startDate AND :endDate " +
           "AND s.priority = :priority ORDER BY s.startTime ASC")
    List<Schedule> findByUserIdAndStartTimeBetweenAndPriority(
        @Param("userId") Long userId,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate,
        @Param("priority") Schedule.Priority priority
    );

    void deleteByCustomerId(Long customerId);
}
