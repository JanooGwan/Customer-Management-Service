package com.example.CustomerManagementService.domain.memo.service;

import com.example.CustomerManagementService.domain.customer.entity.Customer;
import com.example.CustomerManagementService.domain.customer.repository.CustomerRepository;
import com.example.CustomerManagementService.domain.memo.dto.request.MemoCreateRequest;
import com.example.CustomerManagementService.domain.memo.dto.request.MemoUpdateRequest;
import com.example.CustomerManagementService.domain.memo.dto.response.MemoResponse;
import com.example.CustomerManagementService.domain.memo.entity.Memo;
import com.example.CustomerManagementService.domain.memo.repository.MemoRepository;
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
public class MemoService {

    private final MemoRepository memoRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    public MemoResponse create(Long userId, Long customerId, MemoCreateRequest request) {
        Customer customer = customerRepository.findByUserIdAndIdAndDeletedAtIsNull(userId, customerId)
                .orElseThrow(() -> new CustomException(ErrorCode.CUSTOMER_NOT_FOUND));

        Memo memo = request.toEntity(customer);
        Memo savedMemo = memoRepository.save(memo);
        return MemoResponse.from(savedMemo);
    }

    public List<MemoResponse> findAllByCustomerId(Long userId, Long customerId) {
        Customer customer = customerRepository.findByUserIdAndIdAndDeletedAtIsNull(userId, customerId)
                .orElseThrow(() -> new CustomException(ErrorCode.CUSTOMER_NOT_FOUND));

        List<Memo> memos = memoRepository.findByCustomerIdOrderByUpdatedAtDesc(customerId);
        return memos.stream()
                .map(MemoResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public MemoResponse update(Long userId, Long customerId, Long memoId, MemoUpdateRequest request) {
        Customer customer = customerRepository.findByUserIdAndIdAndDeletedAtIsNull(userId, customerId)
                .orElseThrow(() -> new CustomException(ErrorCode.CUSTOMER_NOT_FOUND));

        Memo memo = memoRepository.findByIdAndCustomerId(memoId, customerId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMO_NOT_FOUND));

        memo.updateContent(request.getContent());
        return MemoResponse.from(memo);
    }

    @Transactional
    public void delete(Long userId, Long customerId, Long memoId) {
        Customer customer = customerRepository.findByUserIdAndIdAndDeletedAtIsNull(userId, customerId)
                .orElseThrow(() -> new CustomException(ErrorCode.CUSTOMER_NOT_FOUND));

        Memo memo = memoRepository.findByIdAndCustomerId(memoId, customerId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMO_NOT_FOUND));

        memoRepository.delete(memo);
    }
}
