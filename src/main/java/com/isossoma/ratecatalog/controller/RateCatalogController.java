package com.isossoma.ratecatalog.controller;

import com.isossoma.ratecatalog.service.RateCatalogService;
import com.isossoma.shared.dto.ApiResponse;
import com.isossoma.shared.responses.ResponseBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rate-catalog")
@RequiredArgsConstructor
public class RateCatalogController {
    private final RateCatalogService rateCatalogService;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody Object createRateCatalogRequest) {
        return ResponseBuilder.created("",null);
    }
}