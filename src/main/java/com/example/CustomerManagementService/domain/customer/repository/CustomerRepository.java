package com.example.CustomerManagementService.domain.customer.repository;

import com.example.CustomerManagementService.domain.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByUserIdAndDeletedAtIsNull(Long userId);

    Optional<Customer> findByUserIdAndIdAndDeletedAtIsNull(Long userId, Long id);

    Optional<Customer> findByPhone(String phone);

    boolean existsByPhone(String phone);

    List<Customer> findByUserIdAndDeletedAtIsNullOrderByCreatedAtDesc(Long userId);

    List<Customer> findByUserIdAndCategoryIdAndDeletedAtIsNull(Long userId, Long categoryId);

    @Query("SELECT c FROM Customer c WHERE c.user.id = :userId AND c.deletedAt IS NULL " +
           "AND c.lastContactAt BETWEEN :startDate AND :endDate ORDER BY c.lastContactAt DESC")
    List<Customer> findByUserIdAndLastContactAtBetween(
        @Param("userId") Long userId,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT c FROM Customer c WHERE c.user.id = :userId AND c.deletedAt IS NULL " +
           "AND (c.name LIKE %:keyword% OR c.phone LIKE %:keyword% OR c.email LIKE %:keyword%)")
    List<Customer> searchByKeyword(@Param("userId") Long userId, @Param("keyword") String keyword);
}
