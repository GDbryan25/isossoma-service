package com.isossoma.auth.dto.response;

import com.isossoma.auth.dto.response.access.AccessProfileResponse;

public record AuthenticationResponse(
        String accessToken,
        String refreshToken,
        String tokenType,
        AccessProfileResponse accessProfile
) {
    public AuthenticationResponse(String accessToken, String refreshToken) {
        this(accessToken, refreshToken, "Bearer", null);
    }

    public AuthenticationResponse(String accessToken, String refreshToken, AccessProfileResponse accessProfile) {
        this(accessToken, refreshToken, "Bearer", accessProfile);
    }
}