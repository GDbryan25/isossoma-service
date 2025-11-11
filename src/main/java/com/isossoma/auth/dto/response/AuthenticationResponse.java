package com.isossoma.auth.dto.response;

public record AuthenticationResponse(
        String accessToken,
        String refreshToken,
        String tokenType
) {
    public AuthenticationResponse(String accessToken, String refreshToken) {
        this(accessToken, refreshToken, "Bearer");
    }
}