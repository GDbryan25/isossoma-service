package com.isossoma.customer.controller;

import com.isossoma.customer.dto.filter.CustomerFilter;
import com.isossoma.customer.dto.request.SaveCustomer;
import com.isossoma.customer.dto.response.CustomerResponse;
import com.isossoma.customer.service.CustomerService;
import com.isossoma.shared.dto.ApiResponse;
import com.isossoma.shared.message.SuccessMessages;
import com.isossoma.shared.responses.ResponseBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService service;

    @GetMapping
    public ResponseEntity<ApiResponse> listAllCustomers(@ModelAttribute CustomerFilter filter) {
        Page<CustomerResponse> customers = service.findAll(filter);

        return ResponseBuilder.ok(
                SuccessMessages.QUERY_SUCCESSFULLY,
                customers
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody SaveCustomer customer) {
        CustomerResponse customerResponse = service.create(customer);

        return ResponseBuilder.created(SuccessMessages.CUSTOMER_CREATED,  customerResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @Valid @RequestBody SaveCustomer customer) {
        CustomerResponse customerResponse = service.update(id, customer);

        return ResponseBuilder.ok(SuccessMessages.CUSTOMER_UPDATED, customerResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        Long idDeleted = service.delete(id);

        return ResponseBuilder.ok(SuccessMessages.CUSTOMER_DELETED, idDeleted);
    }

    @PatchMapping("/{id}/reactivate")
    public ResponseEntity<ApiResponse> reactivate(@PathVariable Long id) {
        Long idReactivated = service.reactivate(id);

        return ResponseBuilder.ok("Registro reactivado correctamente", idReactivated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findById(@PathVariable Long id) {
        CustomerResponse customer = service.findById(id);

        return ResponseBuilder.ok(SuccessMessages.CUSTOMER_DELETED, customer);
    }
}