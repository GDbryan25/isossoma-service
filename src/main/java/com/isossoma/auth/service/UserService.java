package com.isossoma.auth.service;

import com.isossoma.auth.dto.request.CreateUserRequest;
import com.isossoma.auth.dto.request.UpdateUserRequest;
import com.isossoma.auth.dto.response.GetAllUsers;
import com.isossoma.auth.dto.response.UserWithRolesResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    void createUser(CreateUserRequest request);
    UserWithRolesResponse getUserById(Long id);
    Page<GetAllUsers> listUsers(Pageable pageable);
    void deleteUser(Long userId);
    void updateUser(Long userId, UpdateUserRequest request);
    void removeRoleFromUser(Long userId, Long roleId);
}