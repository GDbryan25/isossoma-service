package com.isossoma.customer.dto.request;

public record CreateCustomerRequest(
        String name,
        String address,
        String contact,
        String email,
        String cellphone,
        String ruc
) {}