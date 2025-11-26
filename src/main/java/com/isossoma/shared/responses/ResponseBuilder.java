package com.isossoma.shared.responses;

import com.isossoma.shared.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;

public class ResponseBuilder {
    public static ResponseEntity<ApiResponse> created(String message, Object data) {
        ApiResponse response = new ApiResponse(true, message, data, OffsetDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public static ResponseEntity<ApiResponse> ok(String message, Object data) {
        ApiResponse response = new ApiResponse(true, message, data, OffsetDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static ResponseEntity<ApiResponse> notFound(String message) {
        ApiResponse response = new ApiResponse(true, message, null, OffsetDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<ApiResponse> internalServerError(String message) {
        ApiResponse response = new ApiResponse(true, message, null, OffsetDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<ApiResponse> badRequest(String message) {
        ApiResponse response = new ApiResponse(true, message, null, OffsetDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}