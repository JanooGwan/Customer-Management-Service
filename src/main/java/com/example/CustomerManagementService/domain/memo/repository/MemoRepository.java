package com.example.CustomerManagementService.domain.memo.repository;

import com.example.CustomerManagementService.domain.memo.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemoRepository extends JpaRepository<Memo, Long> {

    List<Memo> findByCustomerIdOrderByUpdatedAtDesc(Long customerId);

    Optional<Memo> findByIdAndCustomerId(Long id, Long customerId);

    void deleteByCustomerId(Long customerId);
}
