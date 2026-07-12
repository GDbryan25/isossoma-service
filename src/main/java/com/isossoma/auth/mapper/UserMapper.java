package com.isossoma.auth.mapper;

import com.isossoma.auth.dto.request.CreateUserRequest;
import com.isossoma.auth.dto.response.user.UserResponse;
import com.isossoma.auth.models.entities.User;
import com.isossoma.auth.models.valueobjects.UserInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    public UserInformation toUserInformation(CreateUserRequest createUserRequest) {
        return UserInformation.builder()
                .username(createUserRequest.username())
                .email(createUserRequest.email())
                .password(passwordEncoder.encode(createUserRequest.password()))
                .firstName(createUserRequest.firstname())
                .lastName(createUserRequest.lastname())
                .build();
    }

    public UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstname(),
                user.getLastname(),
                user.getStatus()
        );
    }
}
