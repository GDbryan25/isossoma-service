package com.isossoma.auth.service.impl;

import com.isossoma.auth.dto.response.PermissionSimpleResponse;
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

    @Override
    @Transactional(readOnly = true)
    public List<PermissionSimpleResponse> getAllPermissions() {

        return permissionRepository.findAll()
                .stream()
                .map(p -> new PermissionSimpleResponse(
                        p.getId(),
                        p.getCode(),
                        p.getDescription(),
                        p.getIsActive()
                ))
                .toList();
    }
}