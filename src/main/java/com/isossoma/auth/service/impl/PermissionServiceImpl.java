package com.isossoma.auth.service.impl;

import com.isossoma.auth.dto.response.permission.PermissionResponse;
import com.isossoma.auth.mapper.PermissionMapper;
import com.isossoma.auth.repository.PermissionRepository;
import com.isossoma.auth.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;
    private final PermissionMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<PermissionResponse> findAll(String name) {
        return permissionRepository.findAllByOptionalName(name)
                .stream()
                .map(mapper::toPermissionResponse)
                .toList();
    }
}