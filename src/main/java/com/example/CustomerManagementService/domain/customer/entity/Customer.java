package com.example.CustomerManagementService.domain.customer.entity;

import com.example.CustomerManagementService.domain.auth.entity.User;
import com.example.CustomerManagementService.domain.category.entity.Category;
import com.example.CustomerManagementService.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "customer",
    indexes = {
        @Index(name = "idx_user_id", columnList = "user_id"),
        @Index(name = "idx_phone", columnList = "phone"),
        @Index(name = "idx_created_at", columnList = "created_at")
    }
)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, unique = true, length = 20)
    private String phone;

    @Column(length = 100)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column
    private LocalDateTime lastContactAt;

    public void updateInfo(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public void updateCategory(Category category) {
        this.category = category;
    }

    public void updateLastContactAt() {
        this.lastContactAt = LocalDateTime.now();
    }

    public void removeCategory() {
        this.category = null;
    }
}
