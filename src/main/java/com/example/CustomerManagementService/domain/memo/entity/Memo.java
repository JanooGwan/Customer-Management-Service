package com.example.CustomerManagementService.domain.memo.entity;

import com.example.CustomerManagementService.domain.customer.entity.Customer;
import com.example.CustomerManagementService.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "memo",
    indexes = {
        @Index(name = "idx_customer_id", columnList = "customer_id")
    }
)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Memo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    public void updateContent(String newContent) {
        this.content = newContent;
    }
}
