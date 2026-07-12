package com.isossoma.auth.service;

import com.isossoma.auth.dto.response.permission.PermissionResponse;
import java.util.List;

public interface PermissionService {
    List<PermissionResponse> findAll(String name);
}