package com.isossoma.ratecatalog.controller;

import com.isossoma.ratecatalog.dto.request.CreateServiceItem;
import com.isossoma.ratecatalog.dto.request.UpdateServiceItem;
import com.isossoma.ratecatalog.dto.response.GetItemDetails;
import com.isossoma.ratecatalog.dto.response.GetServiceItem;
import com.isossoma.ratecatalog.service.ItemService;
import com.isossoma.shared.dto.ApiResponse;
import com.isossoma.shared.message.SuccessMessages;
import com.isossoma.shared.responses.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/service-items")
@RequiredArgsConstructor
public class ServiceItemController {
    private final ItemService service;

    @GetMapping
    public ResponseEntity<ApiResponse> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        Page<GetServiceItem> items = service.getAll(page, size);
        return ResponseBuilder.ok(SuccessMessages.QUERY_SUCCESSFULLY, items);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody CreateServiceItem createServiceItem) {
        service.create(createServiceItem);
        return ResponseBuilder.created(
                "Item creado correctamente",
                null);
    }

    @PutMapping
    public ResponseEntity<ApiResponse> update(@RequestBody UpdateServiceItem updateServiceItem) {
        service.update(updateServiceItem);
        return ResponseBuilder.ok(
                "Item actualizado correctamente",
                null
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        service.softDeleteServiceItem(id);
        return ResponseBuilder.ok(
                "Item eliminado correctamente",
                null
        );
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<ApiResponse> getDetails(@PathVariable Long id) {
        GetItemDetails details = service.getDetails(id);
        return ResponseBuilder.ok(SuccessMessages.QUERY_SUCCESSFULLY, details);
    }
}