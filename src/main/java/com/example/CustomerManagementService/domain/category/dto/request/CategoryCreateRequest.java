package com.example.CustomerManagementService.domain.category.dto.request;

import com.example.CustomerManagementService.domain.auth.entity.User;
import com.example.CustomerManagementService.domain.category.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCreateRequest {

    @NotBlank(message = "카테고리명은 필수입니다")
    @Size(max = 50, message = "카테고리명은 최대 50자까지 가능합니다")
    private String name;

    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "색상 코드는 HEX 형식이어야 합니다 (예: #FF5733)")
    private String colorCode;

    public Category toEntity(User user) {
        return Category.builder()
                .user(user)
                .name(this.name)
                .colorCode(this.colorCode)
                .build();
    }
}
