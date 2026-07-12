package com.isossoma.auth.service;

import com.isossoma.auth.dto.filters.UserPageableFilters;
import com.isossoma.auth.dto.request.CreateUserRequest;
import com.isossoma.auth.dto.request.UpdateUserRequest;
import com.isossoma.auth.dto.response.user.UserResponse;
import com.isossoma.auth.dto.response.user.UserDetailResponse;
import org.springframework.data.domain.Page;

public interface UserService {
    UserResponse createUser(CreateUserRequest request);
    UserDetailResponse findById(Long id);
    Page<UserResponse> listUsers(UserPageableFilters filter);
    void deleteUser(Long userId);
    void reactivateUser(Long userId);
    UserResponse updateUser(Long userId, UpdateUserRequest request);
}