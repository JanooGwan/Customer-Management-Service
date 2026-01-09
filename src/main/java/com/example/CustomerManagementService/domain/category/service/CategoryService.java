package com.example.CustomerManagementService.domain.category.service;

import com.example.CustomerManagementService.domain.auth.entity.User;
import com.example.CustomerManagementService.domain.auth.repository.UserRepository;
import com.example.CustomerManagementService.domain.category.dto.request.CategoryCreateRequest;
import com.example.CustomerManagementService.domain.category.dto.request.CategoryUpdateRequest;
import com.example.CustomerManagementService.domain.category.dto.response.CategoryResponse;
import com.example.CustomerManagementService.domain.category.entity.Category;
import com.example.CustomerManagementService.domain.category.repository.CategoryRepository;
import com.example.CustomerManagementService.global.exception.CustomException;
import com.example.CustomerManagementService.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Transactional
    public CategoryResponse create(Long userId, CategoryCreateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (categoryRepository.existsByUserIdAndName(userId, request.getName())) {
            throw new CustomException(ErrorCode.DUPLICATE_CATEGORY_NAME);
        }

        Category category = request.toEntity(user);
        Category savedCategory = categoryRepository.save(category);
        return CategoryResponse.from(savedCategory);
    }

    public CategoryResponse findById(Long userId, Long categoryId) {
        Category category = categoryRepository.findByUserIdAndId(userId, categoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));
        return CategoryResponse.from(category);
    }

    public List<CategoryResponse> findAllByUserId(Long userId) {
        List<Category> categories = categoryRepository.findByUserId(userId);
        return categories.stream()
                .map(CategoryResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public CategoryResponse update(Long userId, Long categoryId, CategoryUpdateRequest request) {
        Category category = categoryRepository.findByUserIdAndId(userId, categoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));

        if (request.getName() != null) {
            if (categoryRepository.existsByUserIdAndName(userId, request.getName())) {
                throw new CustomException(ErrorCode.DUPLICATE_CATEGORY_NAME);
            }
            category.updateName(request.getName());
        }

        if (request.getColorCode() != null) {
            category.updateColorCode(request.getColorCode());
        }

        return CategoryResponse.from(category);
    }

    @Transactional
    public void delete(Long userId, Long categoryId) {
        Category category = categoryRepository.findByUserIdAndId(userId, categoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));
        category.delete();
    }
}
