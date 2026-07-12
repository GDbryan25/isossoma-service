package com.isossoma.auth.mapper;

import com.isossoma.auth.dto.response.permission.PermissionResponse;
import com.isossoma.auth.models.entities.Permission;
import org.springframework.stereotype.Component;

@Component
public class PermissionMapper {
    public PermissionResponse toPermissionResponse(Permission permission) {
        return new PermissionResponse(
                permission.getId(),
                permission.getCode(),
                permission.getDescription(),
                permission.getMenuKey(),
                permission.getSubmenuKey(),
                permission.getActionKey(),
                permission.getRoute(),
                permission.getStatus()
        );
    }
}
