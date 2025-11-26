package com.isossoma.customer.controller;

import com.isossoma.customer.dto.request.SaveCustomerRequest;
import com.isossoma.customer.dto.response.CustomerResponse;
import com.isossoma.customer.service.CustomerService;
import com.isossoma.shared.dto.ApiResponse;
import com.isossoma.shared.message.SuccessMessages;
import com.isossoma.shared.responses.ResponseBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService service;

    @GetMapping
    public ResponseEntity<ApiResponse> listAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseBuilder.ok(SuccessMessages.QUERY_SUCCESSFULLY, service.findAll(page, size));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody SaveCustomerRequest customer) {
        CustomerResponse customerResponse = service.create(customer);

        return ResponseBuilder.created(SuccessMessages.CUSTOMER_CREATED,  customerResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @Valid @RequestBody SaveCustomerRequest customer) {
        CustomerResponse customerResponse = service.update(id, customer);

        return ResponseBuilder.ok(SuccessMessages.CUSTOMER_UPDATED, customerResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        Long idDeleted = service.delete(id);

        return ResponseBuilder.ok(SuccessMessages.CUSTOMER_DELETED, idDeleted);
    }
}