package com.example.CustomerManagementService.domain.auth.dto.response;

import com.example.CustomerManagementService.domain.auth.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private TokenResponse token;
    private UserResponse user;

    public static LoginResponse of(TokenResponse token, User user) {
        return LoginResponse.builder()
                .token(token)
                .user(UserResponse.from(user))
                .build();
    }
}
