package com.isossoma.ratecatalog.controller;

import com.isossoma.ratecatalog.dto.response.GetServiceCategory;
import com.isossoma.ratecatalog.service.CategoryService;
import com.isossoma.shared.dto.ApiResponse;
import com.isossoma.shared.message.SuccessMessages;
import com.isossoma.shared.responses.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/service-categories")
@RequiredArgsConstructor
public class ServiceCategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<GetServiceCategory> categories = categoryService.getAllServiceCategories(PageRequest.of(page, size));
        return ResponseBuilder.ok(SuccessMessages.QUERY_SUCCESSFULLY, categories);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable Long id) {
        GetServiceCategory category = categoryService.getById(id);
        return ResponseBuilder.ok(SuccessMessages.QUERY_SUCCESSFULLY, category);
    }

    @GetMapping("/{id}/service-type")
    public ResponseEntity<ApiResponse> getCategories(@PathVariable Long id) {
        List<GetServiceCategory> categories = categoryService.getCategoriesByServiceType(id);
        return ResponseBuilder.ok(SuccessMessages.QUERY_SUCCESSFULLY, categories);
    }
}