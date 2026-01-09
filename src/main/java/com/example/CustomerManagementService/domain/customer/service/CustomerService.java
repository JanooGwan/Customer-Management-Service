package com.example.CustomerManagementService.domain.customer.service;

import com.example.CustomerManagementService.domain.auth.entity.User;
import com.example.CustomerManagementService.domain.auth.repository.UserRepository;
import com.example.CustomerManagementService.domain.category.entity.Category;
import com.example.CustomerManagementService.domain.category.repository.CategoryRepository;
import com.example.CustomerManagementService.domain.customer.dto.request.CustomerCreateRequest;
import com.example.CustomerManagementService.domain.customer.dto.request.CustomerSearchRequest;
import com.example.CustomerManagementService.domain.customer.dto.request.CustomerUpdateRequest;
import com.example.CustomerManagementService.domain.customer.dto.response.CustomerResponse;
import com.example.CustomerManagementService.domain.customer.entity.Customer;
import com.example.CustomerManagementService.domain.customer.repository.CustomerRepository;
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
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public CustomerResponse create(Long userId, CustomerCreateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (customerRepository.existsByPhone(request.getPhone())) {
            throw new CustomException(ErrorCode.DUPLICATE_PHONE_NUMBER);
        }

        Category category = null;
        if (request.getCategoryId() != null) {
            category = categoryRepository.findByUserIdAndId(userId, request.getCategoryId())
                    .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));
        }

        Customer customer = request.toEntity(user, category);
        Customer savedCustomer = customerRepository.save(customer);
        return CustomerResponse.from(savedCustomer);
    }

    public CustomerResponse findById(Long userId, Long customerId) {
        Customer customer = customerRepository.findByUserIdAndIdAndDeletedAtIsNull(userId, customerId)
                .orElseThrow(() -> new CustomException(ErrorCode.CUSTOMER_NOT_FOUND));
        return CustomerResponse.from(customer);
    }

    public List<CustomerResponse> findAll(Long userId) {
        List<Customer> customers = customerRepository.findByUserIdAndDeletedAtIsNullOrderByCreatedAtDesc(userId);
        return customers.stream()
                .map(CustomerResponse::from)
                .collect(Collectors.toList());
    }

    public List<CustomerResponse> findByCategory(Long userId, Long categoryId) {
        List<Customer> customers = customerRepository.findByUserIdAndCategoryIdAndDeletedAtIsNull(userId, categoryId);
        return customers.stream()
                .map(CustomerResponse::from)
                .collect(Collectors.toList());
    }

    public List<CustomerResponse> search(Long userId, CustomerSearchRequest request) {
        if (request.getKeyword() != null && !request.getKeyword().isEmpty()) {
            List<Customer> customers = customerRepository.searchByKeyword(userId, request.getKeyword());
            return customers.stream()
                    .map(CustomerResponse::from)
                    .collect(Collectors.toList());
        }

        if (request.getCategoryId() != null) {
            return findByCategory(userId, request.getCategoryId());
        }

        if (request.getStartDate() != null && request.getEndDate() != null) {
            List<Customer> customers = customerRepository.findByUserIdAndLastContactAtBetween(
                    userId, request.getStartDate(), request.getEndDate());
            return customers.stream()
                    .map(CustomerResponse::from)
                    .collect(Collectors.toList());
        }

        return findAll(userId);
    }

    @Transactional
    public CustomerResponse update(Long userId, Long customerId, CustomerUpdateRequest request) {
        Customer customer = customerRepository.findByUserIdAndIdAndDeletedAtIsNull(userId, customerId)
                .orElseThrow(() -> new CustomException(ErrorCode.CUSTOMER_NOT_FOUND));

        if (request.getName() != null || request.getPhone() != null || request.getEmail() != null) {
            String name = request.getName() != null ? request.getName() : customer.getName();
            String phone = request.getPhone() != null ? request.getPhone() : customer.getPhone();
            String email = request.getEmail() != null ? request.getEmail() : customer.getEmail();

            if (!phone.equals(customer.getPhone()) && customerRepository.existsByPhone(phone)) {
                throw new CustomException(ErrorCode.DUPLICATE_PHONE_NUMBER);
            }

            customer.updateInfo(name, phone, email);
        }

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findByUserIdAndId(userId, request.getCategoryId())
                    .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));
            customer.updateCategory(category);
        }

        return CustomerResponse.from(customer);
    }

    @Transactional
    public void delete(Long userId, Long customerId) {
        Customer customer = customerRepository.findByUserIdAndIdAndDeletedAtIsNull(userId, customerId)
                .orElseThrow(() -> new CustomException(ErrorCode.CUSTOMER_NOT_FOUND));
        customer.delete();
    }

    @Transactional
    public void updateLastContactAt(Long userId, Long customerId) {
        Customer customer = customerRepository.findByUserIdAndIdAndDeletedAtIsNull(userId, customerId)
                .orElseThrow(() -> new CustomException(ErrorCode.CUSTOMER_NOT_FOUND));
        customer.updateLastContactAt();
    }
}
