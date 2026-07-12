package com.isossoma.ratecatalog.controller;

import com.isossoma.ratecatalog.dto.response.CategoryResponse;
import com.isossoma.ratecatalog.service.CategoryService;
import com.isossoma.shared.dto.ApiResponse;
import com.isossoma.shared.message.SuccessMessages;
import com.isossoma.shared.responses.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/service-category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse> findAll(@RequestParam(required = false) Long serviceId) {
        List<CategoryResponse> categories = categoryService.findAllCategoriesByServiceType(serviceId);

        return ResponseBuilder.ok(SuccessMessages.QUERY_SUCCESSFULLY, categories);
    }
}