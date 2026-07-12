package com.isossoma.auth.controller;

import com.isossoma.auth.dto.request.AuthenticationRequest;
import com.isossoma.auth.dto.request.RegisterRequest;
import com.isossoma.auth.dto.response.AuthenticationResponse;
import com.isossoma.auth.dto.response.access.AccessProfileResponse;
import com.isossoma.auth.service.impl.AuthenticationService;
import com.isossoma.auth.security.UserPrincipal;
import com.isossoma.shared.dto.ApiResponse;
import com.isossoma.shared.message.SuccessMessages;
import com.isossoma.shared.responses.ResponseBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().build();
        }

        String refreshToken = authHeader.substring(7);
        return ResponseEntity.ok(authenticationService.refreshToken(refreshToken));
    }

    @GetMapping("/access-profile")
    public ResponseEntity<ApiResponse> getAccessProfile(Authentication authentication) {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        AccessProfileResponse profile = authenticationService.getAccessProfile(principal.getUsername());

        return ResponseBuilder.ok(SuccessMessages.QUERY_SUCCESSFULLY, profile);
    }
}