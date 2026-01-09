package com.example.CustomerManagementService.domain.category.entity;

import com.example.CustomerManagementService.domain.auth.entity.User;
import com.example.CustomerManagementService.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "category")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 7)
    private String colorCode;

    public void updateName(String newName) {
        this.name = newName;
    }

    public void updateColorCode(String newColorCode) {
        this.colorCode = newColorCode;
    }
}
