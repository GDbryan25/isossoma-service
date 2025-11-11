package com.isossoma.customer.controller;

import com.isossoma.customer.dto.request.CreateCustomerRequest;
import com.isossoma.customer.service.CustomerService;
import com.isossoma.shared.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService service;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody CreateCustomerRequest customer) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse(
                        true,
                        "",
                        null,
                        OffsetDateTime.now()
                ));
    }
}