package com.example.CustomerManagementService.domain.category.repository;

import com.example.CustomerManagementService.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByUserId(Long userId);

    Optional<Category> findByUserIdAndId(Long userId, Long id);

    boolean existsByUserIdAndName(Long userId, String name);
}
