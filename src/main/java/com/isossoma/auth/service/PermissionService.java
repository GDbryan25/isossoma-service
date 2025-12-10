package com.isossoma.auth.service;

import com.isossoma.auth.dto.response.PermissionSimpleResponse;

import java.util.List;

public interface PermissionService {
    List<PermissionSimpleResponse> getAllPermissions();
}