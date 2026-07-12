package com.isossoma.auth.security;

import com.isossoma.auth.models.entities.Permission;
import org.springframework.util.StringUtils;

import java.util.Locale;

public final class PermissionAuthorityResolver {
    private PermissionAuthorityResolver() {}

    public static String resolvePermissionAuthority(Permission permission) {
        if (StringUtils.hasText(permission.getCode())) {
            return normalizeAuthority(permission.getCode());
        }

        if (StringUtils.hasText(permission.getDescription())) {
            return normalizeAuthority(permission.getDescription());
        }

        return "PERMISSION_" + permission.getId();
    }

    public static PermissionScope resolvePermissionScope(Permission permission) {
        String menuKey = normalizeUiKey(permission.getMenuKey());
        String submenuKey = normalizeUiKey(permission.getSubmenuKey());
        String actionKey = normalizeUiKey(permission.getActionKey());

        String candidate = firstNotBlank(permission.getCode(), permission.getDescription());
        if (StringUtils.hasText(candidate) && candidate.contains(".")) {
            String[] tokens = candidate.split("\\.");
            if (tokens.length >= 1 && !StringUtils.hasText(menuKey)) {
                menuKey = normalizeUiKey(tokens[0]);
            }
            if (tokens.length >= 3 && !StringUtils.hasText(submenuKey)) {
                submenuKey = normalizeUiKey(tokens[1]);
            }
            if (tokens.length >= 2 && !StringUtils.hasText(actionKey)) {
                actionKey = normalizeUiKey(tokens[tokens.length - 1]);
            }
        }

        return new PermissionScope(menuKey, submenuKey, actionKey);
    }

    private static String firstNotBlank(String first, String second) {
        if (StringUtils.hasText(first)) {
            return first.trim();
        }
        return StringUtils.hasText(second) ? second.trim() : null;
    }

    private static String normalizeAuthority(String value) {
        return value.trim()
                .replaceAll("\\s+", "_")
                .toUpperCase(Locale.ROOT);
    }

    private static String normalizeUiKey(String value) {
        if (!StringUtils.hasText(value)) {
            return null;
        }
        return value.trim()
                .replaceAll("\\s+", "_")
                .toLowerCase(Locale.ROOT);
    }

    public record PermissionScope(
            String menuKey,
            String submenuKey,
            String actionKey
    ) {}
}
